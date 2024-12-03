package com.sparta.msa_exam.order.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sparta.msa_exam.order.entity.OrderProduct;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OrderProductResponse {
	private Long productId;
	private Integer quantity;
	private Integer totalPrice;

	public OrderProductResponse(OrderProduct orderProduct){
		this.productId = orderProduct.getProductId();
		this.quantity = orderProduct.getQuantity().getValue();
		this.totalPrice = orderProduct.getTotalPrice().getValue();
	}

	@JsonCreator
	public OrderProductResponse(
		@JsonProperty("productId") Long productId,
		@JsonProperty("quantity") Integer quantity,
		@JsonProperty("totalPrice") Integer totalPrice
	) {
		this.productId = productId;
		this.quantity = quantity;
		this.totalPrice = totalPrice;
	}
}
