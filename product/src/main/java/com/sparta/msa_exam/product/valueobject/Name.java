package com.sparta.msa_exam.product.valueobject;

import com.sparta.msa_exam.product.common.code.ProductErrorCode;
import com.sparta.msa_exam.product.common.exception.ProductException;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Name {

	@Column(name = "name", nullable = false)
	String value;

	public Name(String value){
		if(value == null || value.trim().isEmpty()){
			throw new IllegalArgumentException(new ProductException(ProductErrorCode.PRODUCT_NAME_EMPTY));
		}
		this.value = value;
	}
	@Override
	public String toString(){
		return value;
	}
}
