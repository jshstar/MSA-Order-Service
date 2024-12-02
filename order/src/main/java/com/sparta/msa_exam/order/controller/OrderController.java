package com.sparta.msa_exam.order.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sparta.msa_exam.order.aop.RequiresRole;
import com.sparta.msa_exam.order.dto.OrderRequest;
import com.sparta.msa_exam.order.dto.OrderResponse;
import com.sparta.msa_exam.order.service.OrderService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class OrderController {
	private final OrderService orderService;

	@PostMapping("/orders")
	@RequiresRole({"USER","ADMIN"})
	public ResponseEntity<OrderResponse> createOrder(
		@RequestBody OrderRequest orderRequest
	){
		return ResponseEntity.status(HttpStatus.OK).body(orderService.createOrder(orderRequest));
	}
}
