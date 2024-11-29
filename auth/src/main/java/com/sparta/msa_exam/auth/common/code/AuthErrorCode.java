package com.sparta.msa_exam.auth.common.code;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AuthErrorCode {
	NOT_FOUND_USER(HttpStatus.BAD_REQUEST, "회원 정보를 찾을 수 없습니다."),
	EXPIRED_CREDENTIAL(HttpStatus.BAD_REQUEST, "사용자 인증 정보가 만료되었습니다."),
	INSUFFICIENT_PERMISSION(HttpStatus.FORBIDDEN, "사용자에게 접근 권한이 없습니다."),
	INVALID_PASSWORD(HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다."),
	DUPLICATE_USER(HttpStatus.BAD_REQUEST, "중복된 닉네임 입니다.");
	private final HttpStatus httpStatus;
	private final String message;
}
