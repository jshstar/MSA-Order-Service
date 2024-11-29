package com.sparta.msa_exam.auth.common.exception;

import com.sparta.msa_exam.auth.common.code.AuthErrorCode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class AuthException extends RuntimeException{

	private final AuthErrorCode errorCode;
	private final String message;
	public AuthException(AuthErrorCode errorCode){
		this.errorCode =errorCode;
		this.message = errorCode.getMessage();
	}



}
