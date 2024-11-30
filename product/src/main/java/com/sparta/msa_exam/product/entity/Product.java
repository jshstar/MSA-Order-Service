package com.sparta.msa_exam.product.entity;

import com.sparta.msa_exam.product.valueobject.Name;
import com.sparta.msa_exam.product.valueobject.Price;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long Id;

	@Embedded
	private Name name;

	@Embedded
	private Price price;


	public static Product create(Name name, Price price){
		return builder()
			.name(name)
			.price(price)
			.build();
	}

}
