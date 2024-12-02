package com.sparta.msa_exam.gateway.filter;

import java.net.URI;
import java.util.List;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.ReactiveDiscoveryClient;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class WeightedRoutingFilter implements GlobalFilter, Ordered {
	private final ReactiveDiscoveryClient discoveryClient;

	public WeightedRoutingFilter(ReactiveDiscoveryClient discoveryClient) {
		this.discoveryClient = discoveryClient;
	}

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		String path = exchange.getRequest().getURI().getPath();

		if (path.startsWith("/api/v1/products")) {
			return discoveryClient.getInstances("PRODUCT-SERVICE").collectList()
				.flatMap(instances -> {
					if (instances.isEmpty()) {
						log.error("No instances PRODUCT-SERVICE");
						return Mono.error(new IllegalStateException("No instances available for PRODUCT-SERVICE"));
					}


					ServiceInstance chosenInstance = chooseInstanceWithWeight(instances);
					URI targetUri = URI.create(chosenInstance.getUri().toString() + path);

					log.info("Routing URI: {}", targetUri);


					exchange.getAttributes().put("GATEWAY_REQUEST_URL_ATTR", targetUri);

					return chain.filter(exchange.mutate()
						.request(exchange.getRequest().mutate().uri(targetUri).build())
						.build());
				});
		}

		return chain.filter(exchange);
	}

	private ServiceInstance chooseInstanceWithWeight(List<ServiceInstance> instances) {
		double random = Math.random();
		int index = (random < 0.7) ? 0 : 1;
		return instances.get(index % instances.size());
	}

	@Override
	public int getOrder() {
		return -2;
	}
}
