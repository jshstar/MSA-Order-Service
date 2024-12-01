package com.sparta.msa_exam.product.service;

import com.sparta.msa_exam.product.dto.ProductRequest;
import com.sparta.msa_exam.product.dto.ProductResponse;

public interface ProductService {
	ProductResponse createProduct(ProductRequest productRequest);


}
