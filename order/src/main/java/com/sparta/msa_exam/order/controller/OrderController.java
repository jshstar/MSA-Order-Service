package com.sparta.msa_exam.order.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
		return ResponseEntity.status(HttpStatus.CREATED).body(orderService.createOrder(orderRequest));
	}

	@GetMapping("/orders/{orderId}")
	@RequiresRole({"USER","ADMIN"})
	public ResponseEntity<OrderResponse> getOrder(
		@PathVariable Long orderId
	){
		return ResponseEntity.status(HttpStatus.OK).body(orderService.getOrder(orderId));
	}

	@RequiresRole({"USER","ADMIN"})
	@GetMapping("/orders")
	public ResponseEntity<List<OrderResponse>> getSearchOrder(){
		return ResponseEntity.status(HttpStatus.OK).body(orderService.getSearchOrder());
	}

}
