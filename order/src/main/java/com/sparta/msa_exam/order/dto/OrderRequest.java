package com.sparta.msa_exam.order.dto;

import java.util.List;

import lombok.Getter;

@Getter
public class OrderRequest {
	private String deliveryRequest;

	private List<OrderProductRequest> orderProductList;

}
