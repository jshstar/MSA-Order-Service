package com.sparta.msa_exam.order.client.dto;

import lombok.Getter;

@Getter
public class ProductResponse {
	private final Long id;
	private final String name;
	private final Integer price;

	public ProductResponse(Long id, String name, Integer price){
		this.id = id;
		this.name = name;
		this.price = price;
	}
}
