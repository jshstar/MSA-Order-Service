package com.sparta.msa_exam.order.common.response;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class ExceptionResponse<T> {

    private final HttpStatus status;
    private final String msg;
    private final T data;

    public static <T> ExceptionResponse<T> of(HttpStatus status, String msg, T data) {
        return new ExceptionResponse<>(status, msg, data);
    }
}
