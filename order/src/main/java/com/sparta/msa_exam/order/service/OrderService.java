package com.sparta.msa_exam.order.service;

import java.util.List;

import com.sparta.msa_exam.order.dto.OrderRequest;
import com.sparta.msa_exam.order.dto.OrderResponse;

public interface OrderService {
	OrderResponse createOrder(OrderRequest request);
	OrderResponse getOrder(Long orderId);
	List<OrderResponse> getSearchOrder();
}
