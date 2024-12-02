package com.sparta.msa_exam.gateway.filter;

import java.net.URI;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class CustomAddServerPortHeaderPostFilter implements GlobalFilter, Ordered{
	private static final String SERVER_PORT_HEADER = "Server-Port";

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		return chain.filter(exchange).then(Mono.defer(() -> {
			URI targetUri = exchange.getAttribute("GATEWAY_REQUEST_URL_ATTR");

			if (targetUri != null) {
				String port = String.valueOf(targetUri.getPort());
				ServerHttpResponse response = exchange.getResponse();
				response.getHeaders().add(SERVER_PORT_HEADER, port);
				log.info("Server Port: {}", port);
			} else {
				log.warn("GATEWAY_REQUEST_URL_ATTR is null");
			}

			return Mono.empty();
		}));
	}

	@Override
	public int getOrder() {
		return Ordered.LOWEST_PRECEDENCE;
	}
}
