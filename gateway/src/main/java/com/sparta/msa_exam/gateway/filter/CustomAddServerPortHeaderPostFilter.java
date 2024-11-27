package com.sparta.msa_exam.gateway.filter;

import java.net.URI;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class CustomAddServerPortHeaderPostFilter implements GlobalFilter {
	private static final String SERVER_PORT_HEADER = "Server-Port";

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain){
		return chain.filter(exchange).then(Mono.fromRunnable(() -> addServerPortInResponseHeader(exchange)));
	}

	private void addServerPortInResponseHeader(ServerWebExchange exchange){
		URI uri = exchange.getAttribute(ServerWebExchangeUtils.GATEWAY_REQUEST_URL_ATTR);

		if(uri != null){
			String port = String.valueOf(uri.getPort());
			ServerHttpResponse response = exchange.getResponse();
			response.getHeaders().add(SERVER_PORT_HEADER,port);
		}
	}

}
