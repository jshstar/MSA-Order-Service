package com.sparta.msa_exam.gateway.filter;

import java.net.URI;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.ReactiveDiscoveryClient;
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
	private final ReactiveDiscoveryClient discoveryClient;

	public CustomAddServerPortHeaderPostFilter(ReactiveDiscoveryClient discoveryClient) {
		this.discoveryClient = discoveryClient;
	}
	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		return chain.filter(exchange).then(Mono.defer(() -> {
			URI targetUri = exchange.getAttribute("GATEWAY_REQUEST_URL_ATTR");
			String port;

			if (targetUri != null) {
				port = String.valueOf(targetUri.getPort());
				addPortToResponse(exchange, port);
			} else {
				String serviceId = extractServiceId(exchange.getRequest().getURI().getPath());
				if (serviceId != null) {
					log.info("Attempting service: {}", serviceId);
					return discoveryClient.getInstances(serviceId)
						.collectList()
						.flatMap(instances -> {
							if (instances.isEmpty()) {
								log.warn("No instances service: {}", serviceId);
								addPortToResponse(exchange, "default-port");
								return Mono.empty();
							}

							ServiceInstance chosenInstance = instances.get(0);
							log.info("Chosen instance: {}", chosenInstance.getUri());
							String instancePort = String.valueOf(chosenInstance.getUri().getPort());
							log.info("Discovered instance port: {}", instancePort);
							addPortToResponse(exchange, instancePort);
							return Mono.empty();
						});
				} else {
					log.warn("Unable service ID for path: {}", exchange.getRequest().getURI().getPath());
				}
			}

			return Mono.empty();
		}));
	}

	private void addPortToResponse(ServerWebExchange exchange, String port) {
		ServerHttpResponse response = exchange.getResponse();
		response.getHeaders().add("Server-Port", port);
		log.info("Server Port: {}", port);
	}

	private String extractServiceId(String path) {
		if (path.startsWith("/api/v1/products")) {
			return "PRODUCT-SERVICE";
		}
		// else if (path.startsWith("/api/v1/auth")) {
		// 	return "AUTH-SERVICE";
		// }
		else if (path.startsWith("/api/v1/orders")) {
			return "ORDER-SERVICE"; // 추가: /api/v1/orders에 대해 올바른 서비스 ID 반환
		}
		return null;
	}
	@Override
	public int getOrder() {
		return Ordered.LOWEST_PRECEDENCE;
	}
}
