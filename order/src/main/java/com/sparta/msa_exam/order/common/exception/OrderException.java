package com.sparta.msa_exam.order.common.exception;

import com.sparta.msa_exam.order.common.code.OrderErrorCode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class OrderException extends RuntimeException{

	private final OrderErrorCode errorCode;
	private final String message;
	public OrderException(OrderErrorCode errorCode){
		this.errorCode =errorCode;
		this.message = errorCode.getMessage();
	}



}
