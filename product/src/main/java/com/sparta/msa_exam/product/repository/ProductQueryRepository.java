package com.sparta.msa_exam.product.repository;

import java.util.List;

import com.sparta.msa_exam.product.dto.ProductResponse;

public interface ProductQueryRepository {


	List<ProductResponse> getAllProduct();
	List<ProductResponse> getProductsByIds(List<Long> productIds);


}
