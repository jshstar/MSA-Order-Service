package com.sparta.msa_exam.product.controller;

import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sparta.msa_exam.product.dto.ProductRequest;
import com.sparta.msa_exam.product.dto.ProductResponse;
import com.sparta.msa_exam.product.security.RequiresRole;
import com.sparta.msa_exam.product.service.ProductService;

import lombok.RequiredArgsConstructor;

@RestController
@RefreshScope
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class ProductController {

	private final ProductService productService;

	@PostMapping("/products")
	@RequiresRole("ADMIN")
	public ResponseEntity<ProductResponse> createProduct(
		@RequestBody ProductRequest productRequest
	){
		return ResponseEntity.status(HttpStatus.CREATED).body(productService.createProduct(productRequest));
	}


}
