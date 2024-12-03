package com.sparta.msa_exam.order.dto;

import java.io.Serializable;

import com.sparta.msa_exam.order.entity.OrderProduct;
import com.sparta.msa_exam.order.valueobject.Quantity;
import com.sparta.msa_exam.order.valueobject.TotalPrice;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OrderProductResponse implements Serializable {
	private Long productId;
	private Integer quantity;
	private Integer totalPrice;

	public OrderProductResponse(OrderProduct orderProduct){
		this.productId = orderProduct.getProductId();
		this.quantity = orderProduct.getQuantity().getValue();
		this.totalPrice = orderProduct.getTotalPrice().getValue();
	}

	public OrderProductResponse(
		Long productId,
		Quantity quantity,
		TotalPrice totalPrice
	) {
		this.productId = productId;
		this.quantity = quantity.getValue();
		this.totalPrice = totalPrice.getValue();
	}
}
