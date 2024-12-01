package com.sparta.msa_exam.product.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import com.sparta.msa_exam.product.dto.AuthValidationResponse;

@FeignClient(name = "auth-service", url = "${auth.service.url}")
public interface AuthClient {
	@GetMapping("/api/v1/auth/validate")
	AuthValidationResponse validateRole(
		@RequestHeader("Authorization") String token);

}
