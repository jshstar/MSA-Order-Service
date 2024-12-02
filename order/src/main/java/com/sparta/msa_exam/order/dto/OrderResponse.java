package com.sparta.msa_exam.order.dto;

import java.util.List;

import com.sparta.msa_exam.order.entity.Order;
import com.sparta.msa_exam.order.valueobject.DeliveryRequest;

import lombok.Getter;

@Getter
public class OrderResponse {
	private Long orderId;
	private String deliveryRequest;
	private List<OrderProductResponse> productIds;

	public OrderResponse(Order order){
		this.orderId = order.getId();
		this.deliveryRequest = order.getDeliveryRequest().getValue();
		this.productIds = order.getOrderProducts().stream().map(OrderProductResponse::new).toList();
	}

	public OrderResponse(Long orderId, DeliveryRequest deliveryRequest, List<OrderProductResponse> productIds){
		this.orderId = orderId;
		this.deliveryRequest = deliveryRequest.getValue();
		this.productIds = productIds;
	}
}
