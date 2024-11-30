package com.sparta.msa_exam.product.service;

import org.springframework.stereotype.Service;

import com.sparta.msa_exam.product.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService{

	private final ProductRepository productRepository;



}
