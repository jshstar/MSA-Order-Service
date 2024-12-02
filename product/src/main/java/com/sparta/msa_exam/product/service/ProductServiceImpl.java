package com.sparta.msa_exam.product.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sparta.msa_exam.product.common.code.ProductErrorCode;
import com.sparta.msa_exam.product.common.exception.ProductException;
import com.sparta.msa_exam.product.dto.ProductRequest;
import com.sparta.msa_exam.product.dto.ProductResponse;
import com.sparta.msa_exam.product.entity.Product;
import com.sparta.msa_exam.product.repository.ProductQueryRepository;
import com.sparta.msa_exam.product.repository.ProductRepository;
import com.sparta.msa_exam.product.valueobject.Name;
import com.sparta.msa_exam.product.valueobject.Price;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

	private final ProductRepository productRepository;
	private final ProductQueryRepository productQueryRepository;

	@Override
	@Transactional
	public ProductResponse createProduct(ProductRequest productRequest) {
		Name name = new Name(productRequest.getName());
		validateProduct(name);
		Price price = new Price(productRequest.getPrice());
		Product product = Product.create(name, price);
		return new ProductResponse(productRepository.save(product));
	}


	@Override
	@Transactional(readOnly = true)
	public List<ProductResponse> getProductsByIds(List<Long> productIds){
		List<ProductResponse> productList = productQueryRepository.getProductsByIds(productIds);
		validateProducts(productIds, productList);
		return productList;
	}

	@Override
	@Transactional(readOnly = true)
	public List<ProductResponse> getAllProduct(){
		return productQueryRepository.getAllProduct();
	}

	private void validateProduct(Name name) {
		if (productRepository.existsByName(name)) {
			throw new IllegalArgumentException(new ProductException(ProductErrorCode.PRODUCT_NAME_DUPLICATE));
		}
	}

	private void validateProducts(List<Long> productIds, List<ProductResponse> productList){
		if(productIds.size() != productList.size()){
			throw new IllegalArgumentException(new ProductException(ProductErrorCode.PRODUCT_NAME_DUPLICATE));
		}
		for (ProductResponse productResponse : productList){
			if(!productIds.contains(productResponse.getId())){
				throw new IllegalArgumentException(new ProductException(ProductErrorCode.PRODUCT_NAME_DUPLICATE));
			}
		}
	}



}
