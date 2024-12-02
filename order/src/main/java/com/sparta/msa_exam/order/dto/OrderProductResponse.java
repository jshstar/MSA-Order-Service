package com.sparta.msa_exam.order.dto;

import com.sparta.msa_exam.order.entity.OrderProduct;

import lombok.Getter;

@Getter
public class OrderProductResponse {
	private Long productId;
	private Integer quantity;
	private Integer totalPrice;

	public OrderProductResponse(OrderProduct orderProduct){
		this.productId = orderProduct.getProductId();
		this.quantity = orderProduct.getQuantity().getValue();
		this.totalPrice = orderProduct.getTotalPrice().getValue();
	}
}
