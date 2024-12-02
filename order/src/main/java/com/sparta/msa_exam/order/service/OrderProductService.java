package com.sparta.msa_exam.order.service;

import java.util.List;

import com.sparta.msa_exam.order.dto.OrderProductRequest;
import com.sparta.msa_exam.order.entity.Order;
import com.sparta.msa_exam.order.entity.OrderProduct;

public interface OrderProductService {
	List<OrderProduct> createOrderProduct(List<OrderProductRequest> orderProducts, Order order);
}
