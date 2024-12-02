package com.sparta.msa_exam.order.dto;

import lombok.Getter;

@Getter
public class OrderProductRequest {
	private Long productId;
	private Integer quantity;

}
