package com.sparta.msa_exam.product.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.sparta.msa_exam.product.common.response.ExceptionResponse;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {


	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<ExceptionResponse<Void>> handleRuntimeException(RuntimeException e) {
		log.error("RuntimeException: ", e);
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
			.body(ExceptionResponse.of(HttpStatus.INTERNAL_SERVER_ERROR, "서버 에러", null));
	}

	@ExceptionHandler(ProductException.class)
	public ResponseEntity<ExceptionResponse<Void>> customExceptionHandler(ProductException e) {
		log.error("CustomException: "+ e);
		return ResponseEntity.status(e.getErrorCode().getHttpStatus()).body(
			ExceptionResponse.of(
				e.getErrorCode().getHttpStatus(),
				e.getErrorCode().getMessage(),
				null
			)
		);
	}

}
