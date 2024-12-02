package com.sparta.msa_exam.product.service;

import java.util.List;

import com.sparta.msa_exam.product.dto.ProductRequest;
import com.sparta.msa_exam.product.dto.ProductResponse;

public interface ProductService {
	ProductResponse createProduct(ProductRequest productRequest);

	List<ProductResponse> getProductsByIds(List<Long> productIds);
	List<ProductResponse> getAllProduct();

}
