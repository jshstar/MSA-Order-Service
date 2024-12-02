package com.sparta.msa_exam.order.common.code;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OrderErrorCode {
	NOT_FOUND_ORDER(HttpStatus.BAD_REQUEST, "주문 정보를 찾을 수 없습니다."),
	INVALID_USER_ROLE(HttpStatus.FORBIDDEN, "해당 유저는 접근이 불가한 서비스입니다.");
	private final HttpStatus httpStatus;
	private final String message;
}
