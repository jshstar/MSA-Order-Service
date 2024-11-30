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
public class Price {

	@Column(name = "price", nullable = false)
	Integer value;

	public Price(Integer value){
		if(value == null){
			throw new IllegalArgumentException(new ProductException(ProductErrorCode.PRODUCT_PRICE_EMPTY));
		}
		if(value < 0){
			throw new IllegalArgumentException(new ProductException(ProductErrorCode.PRODUCT_PRICE_EMPTY));
		}
		this.value = value;
	}

	@Override
	public String toString() {
		return "Price{" +
			"value=" + value +
			'}';
	}
}
