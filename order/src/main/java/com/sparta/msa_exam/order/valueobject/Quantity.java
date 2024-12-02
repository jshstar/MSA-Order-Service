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
public class Quantity {

	@Column(name = "quantity", nullable = false)
	Integer value;

	public Quantity(Integer value){
		if(value == null){
			throw new IllegalArgumentException(new OrderException(OrderErrorCode.PRODUCT_QUANTITY_EMPTY));
		}
		if(value < 0){
			throw new IllegalArgumentException(new OrderException(OrderErrorCode.PRODUCT_QUANTITY_NOT_MINUS));
		}
		this.value = value;
	}

}
