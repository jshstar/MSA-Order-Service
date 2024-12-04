package com.sparta.msa_exam.order.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sparta.msa_exam.order.client.dto.ProductResponse;
import com.sparta.msa_exam.order.service.ProductService;
@FeignClient(name = "product-service", fallback = ProductFallbackClient.class)
@Primary
public interface ProductClient extends ProductService {
	@GetMapping("/api/v1/products/internal")
	List<ProductResponse> getProductsByIds(
		@RequestParam List<Long> productIds);
}
