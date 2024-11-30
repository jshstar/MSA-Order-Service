package com.sparta.msa_exam.product.common.exception;

import com.sparta.msa_exam.product.common.code.ProductErrorCode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ProductException extends RuntimeException{

	private final ProductErrorCode errorCode;
	private final String message;
	public ProductException(ProductErrorCode errorCode){
		this.errorCode =errorCode;
		this.message = errorCode.getMessage();
	}



}
