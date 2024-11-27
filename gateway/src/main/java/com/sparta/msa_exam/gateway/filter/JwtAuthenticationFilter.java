package com.sparta.msa_exam.gateway.filter;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
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
public class JwtAuthenticationFilter implements GlobalFilter {

	@Value("${service.jwt.secret-key}")
	private String secretKey;

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		String path = exchange.getRequest().getURI().getPath();
		if (path.equals("/auth/signIn") || path.equals("/auth/signUp")) {
			return chain.filter(exchange);
		}

		String token = extractToken(exchange);

		if (token == null || !validateToken(token)) {
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

	private boolean validateToken(String token) {
		try {
			SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(secretKey));
			Jws<Claims> claimsJws = Jwts.parser()
				.verifyWith(key)
				.build().parseSignedClaims(token);



			return true;
		} catch (Exception e) {
			return false;
		}
	}
}
