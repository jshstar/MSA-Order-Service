package com.sparta.msa_exam.order.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sparta.msa_exam.order.entity.Order;
import com.sparta.msa_exam.order.valueobject.DeliveryRequest;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OrderResponse {
	private Long orderId;
	private String deliveryRequest;
	private List<OrderProductResponse> productIds;

	public OrderResponse(Order order){
		this.orderId = order.getId();
		this.deliveryRequest = order.getDeliveryRequest().getValue();
		this.productIds = order.getOrderProducts().stream().map(OrderProductResponse::new).toList();
	}

	@JsonCreator
	public OrderResponse(
		@JsonProperty("orderId") Long orderId,
		@JsonProperty("deliveryRequest") String deliveryRequest,
		@JsonProperty("productIds") List<OrderProductResponse> productIds
	) {
		this.orderId = orderId;
		this.deliveryRequest = deliveryRequest;
		this.productIds = productIds;
	}

	public OrderResponse(Long orderId, DeliveryRequest deliveryRequest, List<OrderProductResponse> productIds){
		this.orderId = orderId;
		this.deliveryRequest = deliveryRequest.getValue();
		this.productIds = productIds;
	}
}
