package com.sparta.msa_exam.order.service;

import java.util.List;

import com.sparta.msa_exam.order.dto.OrderProductResponse;
import com.sparta.msa_exam.order.dto.OrderRequest;
import com.sparta.msa_exam.order.dto.OrderResponse;
import com.sparta.msa_exam.order.dto.OrderUpdateRequest;

public interface OrderService {
	OrderResponse createOrder(OrderRequest request);
	List<OrderProductResponse> getOrder(Long orderId);
	List<OrderResponse> getSearchOrder();
	OrderResponse updateOrder(OrderUpdateRequest request, Long orderId);
}
