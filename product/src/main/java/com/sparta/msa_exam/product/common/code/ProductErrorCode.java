package com.sparta.msa_exam.product.common.code;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ProductErrorCode {
	NOT_FOUND_PRODUCT(HttpStatus.BAD_REQUEST, "회원 정보를 찾을 수 없습니다."),
	PRODUCT_NAME_EMPTY(HttpStatus.BAD_REQUEST, "상품명이 비어있습니다."),
	PRODUCT_PRICE_EMPTY(HttpStatus.BAD_REQUEST, "가격이 비어있습니다."),
	PRODUCT_NAME_DUPLICATE(HttpStatus.BAD_REQUEST, "가격이 비어있습니다."),
	REQUEST_RESULT_COUNT_MISMATCH(HttpStatus.BAD_REQUEST, "상품 데이터 개수 불일치"),
	REQUEST_RESULT_VALUE_MISMATCH(HttpStatus.UNPROCESSABLE_ENTITY, "요청한 데이터의 일부가 일치하지 않습니다."),
	INVALID_PRODUCT_PRICE(HttpStatus.BAD_REQUEST, "가격은 음수값이 될 수 없습니다."),
	INVALID_USER_ROLE(HttpStatus.FORBIDDEN, "해당 유저는 접근이 불가한 서비스입니다.");

	private final HttpStatus httpStatus;
	private final String message;
}
