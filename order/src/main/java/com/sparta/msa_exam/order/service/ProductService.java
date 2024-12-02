package com.sparta.msa_exam.order.service;

import java.util.List;

import com.sparta.msa_exam.order.client.dto.ProductResponse;

public interface ProductService {
	List<ProductResponse> getProductsByIds(List<Long> productIds);
}
