package com.sparta.msa_exam.gateway.filter;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.ReactiveDiscoveryClient;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class JwtAuthenticationFilter implements GlobalFilter, Ordered {

	@Value("${service.jwt.secret-key}")
	private String secretKey;
	private final ReactiveDiscoveryClient discoveryClient;

	public JwtAuthenticationFilter(ReactiveDiscoveryClient discoveryClient) {
		this.discoveryClient = discoveryClient;
	}

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		String path = exchange.getRequest().getURI().getPath();

		if (isPublicPath(path)) {
			if (path.equals("/api/v1/auth/signIn") || path.equals("/api/v1/auth/signUp")) {
				return discoveryClient.getInstances("AUTH-SERVICE").collectList()
					.flatMap(instances -> {
						if (instances.isEmpty()) {
							log.error("No instances AUTH-SERVICE");
							return Mono.error(new IllegalStateException("No instances AUTH-SERVICE"));
						}

						ServiceInstance chosenInstance = instances.get(0);
						String port = String.valueOf(chosenInstance.getUri().getPort());

						exchange.getResponse().getHeaders().add("Server-Port", port);
						log.info("Server-Port {}: {}", path, port);

						return chain.filter(exchange);
					});
			}
			return chain.filter(exchange);
		}

		String token = extractToken(exchange);

		if (token == null || !validateToken(token, exchange)) {
			exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
			return exchange.getResponse().setComplete();
		}

		return chain.filter(exchange);
	}
	private String extractToken(ServerWebExchange exchange) {
		String authHeader = exchange.getRequest().getHeaders().getFirst("Authorization");
		if (authHeader != null && authHeader.startsWith("Bearer ")) {
			return authHeader.substring(7);
		}
		return null;
	}

	private boolean validateToken(String token, ServerWebExchange exchange) {
		try {
			SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(secretKey));
			Jws<Claims> claims = Jwts.parserBuilder()
				.setSigningKey(key)
				.build()
				.parseClaimsJws(token);

			log.info("Authenticated user: {}", claims.getBody().get("username"));

			exchange.getAttributes().put("claims", claims.getBody());
			return true;
		} catch (Exception e) {
			log.error("Invalid JWT: {}", e.getMessage());
			return false;
		}
	}

	private boolean isPublicPath(String path) {
		return path.equals("/api/v1/auth/signIn") ||
			path.equals("/api/v1/auth/signUp") ||
			path.equals("/api/v1/products");
	}

	@Override
	public int getOrder() {
		return 0;
	}
}
