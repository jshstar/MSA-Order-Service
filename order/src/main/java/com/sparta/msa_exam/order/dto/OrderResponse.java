package com.sparta.msa_exam.order.dto;

import java.io.Serializable;
import java.util.List;

import com.sparta.msa_exam.order.entity.Order;
import com.sparta.msa_exam.order.entity.OrderProduct;
import com.sparta.msa_exam.order.valueobject.DeliveryRequest;
import com.sparta.msa_exam.order.valueobject.OrderTotalPrice;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OrderResponse implements Serializable {
	private Long orderId;
	private String deliveryRequest;
	private Integer totalPrice;
	private List<Long> productIds;

	public OrderResponse(Order order) {
		this.orderId = order.getId();
		this.deliveryRequest = order.getDeliveryRequest().getValue();
		this.totalPrice = order.getTotalPrice().getValue();
		this.productIds = order.getOrderProducts().stream().map(OrderProduct::getProductId).toList();
	}

	public OrderResponse(
		Long orderId,
		DeliveryRequest deliveryRequest,
		OrderTotalPrice orderTotalPrice,
		List<Long> productIds
	) {
		this.orderId = orderId;
		this.deliveryRequest = deliveryRequest.getValue();
		this.totalPrice = orderTotalPrice.getValue();
		this.productIds = productIds;
	}
}
