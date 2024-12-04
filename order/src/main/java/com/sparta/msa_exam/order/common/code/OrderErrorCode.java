package com.sparta.msa_exam.order.common.code;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OrderErrorCode {
	NOT_FOUND_ORDER(HttpStatus.BAD_REQUEST, "주문 정보를 찾을 수 없습니다."),
	NOT_FOUND_PRODUCT(HttpStatus.BAD_REQUEST, "상품정보를 찾을 수 없습니다."),
	INVALID_USER_ROLE(HttpStatus.FORBIDDEN, "해당 유저는 접근이 불가한 서비스입니다."),
	PRODUCT_QUANTITY_EMPTY(HttpStatus.BAD_REQUEST, "수량이 비어있습니다."),
	PRODUCT_QUANTITY_NOT_MINUS(HttpStatus.BAD_REQUEST, "수량은 음수일 수 없습니다."),
	TOTAL_PRICE_NOT_MINUS(HttpStatus.BAD_REQUEST, "수량은 음수일 수 없습니다."),
	TOTAL_PRICE_EMPTY(HttpStatus.BAD_REQUEST, "총 가격이 비어있습니다."),
	PRODUCT_SERVER_ERROR(HttpStatus.NOT_FOUND, "상품 요청 실패"),
	ORDER_TOTAL_PRICE_NOT_MINUS(HttpStatus.BAD_REQUEST, "수량은 음수일 수 없습니다."),
	ORDER_TOTAL_PRICE_EMPTY(HttpStatus.BAD_REQUEST, "총 가격이 비어있습니다."),
	DELIVERY_REQUEST_EMPTY(HttpStatus.BAD_REQUEST, "요청사항은 필수입니다.");
	private final HttpStatus httpStatus;
	private final String message;
}
