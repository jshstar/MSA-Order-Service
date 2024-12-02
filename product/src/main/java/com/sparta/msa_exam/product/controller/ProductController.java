package com.sparta.msa_exam.product.controller;

import java.util.List;

import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sparta.msa_exam.product.dto.ProductRequest;
import com.sparta.msa_exam.product.dto.ProductResponse;
import com.sparta.msa_exam.product.aop.RequiresRole;
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

	@GetMapping("/products/internal")
	public ResponseEntity<List<ProductResponse>> getProductsByIds(
		@RequestParam(value = "productIds") List<Long> productIds
	){
		return  ResponseEntity.status(HttpStatus.OK).body(productService.getProductsByIds(productIds));
	}

	@GetMapping("/products")
	public ResponseEntity<List<ProductResponse>> getAllProduct(){
		return ResponseEntity.status(HttpStatus.OK).body(productService.getAllProduct());
	}


}
