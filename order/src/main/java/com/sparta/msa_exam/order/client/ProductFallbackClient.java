package com.sparta.msa_exam.order.client;

import java.util.List;

import org.springframework.stereotype.Component;

import com.sparta.msa_exam.order.client.dto.ProductResponse;
import com.sparta.msa_exam.order.common.code.OrderErrorCode;
import com.sparta.msa_exam.order.common.exception.OrderException;

@Component
public class ProductFallbackClient implements ProductClient{

	@Override
	public List<ProductResponse> getProductsByIds(List<Long> productIds){

		throw new IllegalArgumentException(new OrderException(OrderErrorCode.PRODUCT_SERVER_ERROR));
	}
}
