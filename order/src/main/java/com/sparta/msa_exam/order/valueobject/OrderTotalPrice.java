package com.sparta.msa_exam.order.valueobject;

import com.sparta.msa_exam.order.common.code.OrderErrorCode;
import com.sparta.msa_exam.order.common.exception.OrderException;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderTotalPrice {

	@Column(name = "order_total_price", nullable = false)
	Integer value;

	public OrderTotalPrice(Integer value){
		if(value == null){
			throw new IllegalArgumentException(new OrderException(OrderErrorCode.ORDER_TOTAL_PRICE_EMPTY));
		}
		if(value < 0){
			throw new IllegalArgumentException(new OrderException(OrderErrorCode.ORDER_TOTAL_PRICE_NOT_MINUS));
		}
		this.value = value;
	}

	public static OrderTotalPrice zero() {
		return new OrderTotalPrice(0);
	}

}
