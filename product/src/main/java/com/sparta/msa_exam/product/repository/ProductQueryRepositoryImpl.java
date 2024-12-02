package com.sparta.msa_exam.product.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.msa_exam.product.dto.ProductResponse;
import com.sparta.msa_exam.product.entity.QProduct;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ProductQueryRepositoryImpl implements ProductQueryRepository{

	private final JPAQueryFactory jpaQueryFactory;

	QProduct qProduct = QProduct.product;
	@Override
	public List<ProductResponse> getAllProduct(){
		return jpaQueryFactory.query()
				.select(
					Projections.constructor(
						ProductResponse.class,
						qProduct.Id,
						qProduct.name,
						qProduct.price
					)
				)
				.from(qProduct)
				.fetch();
	}

	public List<ProductResponse> getProductsByIds(List<Long> productIds){
		return jpaQueryFactory.query()
			.select(
				Projections.constructor(
					ProductResponse.class,
					qProduct.Id,
					qProduct.name,
					qProduct.price
				)
			).from(qProduct)
			.where(qProduct.Id.in(productIds))
			.orderBy(qProduct.Id.asc())
			.fetch();
	}

}
