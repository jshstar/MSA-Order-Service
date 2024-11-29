package com.sparta.msa_exam.gateway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Component
@Slf4j
@Order(1)
public class JwtAuthorizationFilter implements GlobalFilter {


	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		String path = exchange.getRequest().getURI().getPath();

		Claims claims = (Claims) exchange.getAttributes().get("claims");

		if (claims == null) {
			log.warn("Missing Claims in request");
			exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
			return exchange.getResponse().setComplete();
		}

		String userRole = claims.get("userRole", String.class);
		if (!hasAccess(userRole, path)) {
			log.warn("UserRole '{}' does not have access to path '{}'", userRole, path);
			exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
			return exchange.getResponse().setComplete();
		}

		return chain.filter(exchange);
	}

	private boolean hasAccess(String userRole, String path) {
		if (path.startsWith("/admin") && !"ADMIN".equals(userRole)) {
			return false;
		}
		return true;
	}


}
