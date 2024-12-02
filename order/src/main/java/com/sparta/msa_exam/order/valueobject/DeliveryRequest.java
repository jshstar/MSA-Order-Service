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
public class DeliveryRequest {

	@Column(name = "delivery_request", nullable = false)
	String value;

	public DeliveryRequest(String value){
		if(value == null || value.trim().isEmpty()){
			throw new IllegalArgumentException(new OrderException(OrderErrorCode.ORDER_DELIVERY_REQUEST_EMPTY));
		}
		this.value = value;
	}
	@Override
	public String toString(){
		return value;
	}
}
