package com.sparta.msa_exam.product.dto;

import com.sparta.msa_exam.product.entity.Product;

import lombok.Getter;

@Getter
public class ProductResponse {
	private final Long id;
	private final String name;
	private final Integer price;

	public ProductResponse(Product product){
		this.id = product.getId();
		this.name = product.getName().getValue();
		this.price = product.getPrice().getValue();
	}

}
