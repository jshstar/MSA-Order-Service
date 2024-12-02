package com.sparta.msa_exam.order.service;

import com.sparta.msa_exam.order.dto.OrderRequest;
import com.sparta.msa_exam.order.dto.OrderResponse;

public interface OrderService {
	OrderResponse createOrder(OrderRequest request);
}
