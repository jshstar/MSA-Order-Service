package com.sparta.msa_exam.product.repository;

import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ProductQueryRepositoryImpl implements ProductQueryRepository{

	private JPAQueryFactory jpaQueryFactory;



}
