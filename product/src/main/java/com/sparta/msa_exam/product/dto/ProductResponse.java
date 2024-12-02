package com.sparta.msa_exam.product.dto;

import com.sparta.msa_exam.product.entity.Product;
import com.sparta.msa_exam.product.valueobject.Name;
import com.sparta.msa_exam.product.valueobject.Price;

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

	public ProductResponse(Long id, Name name, Price price){
		this.id = id;
		this.name = name.getValue();
		this.price = price.getValue();
	}


}
