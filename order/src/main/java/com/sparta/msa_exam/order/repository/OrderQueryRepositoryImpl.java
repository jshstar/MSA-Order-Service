package com.sparta.msa_exam.order.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.msa_exam.order.entity.Order;
import com.sparta.msa_exam.order.entity.QOrder;
import com.sparta.msa_exam.order.entity.QOrderProduct;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class OrderQueryRepositoryImpl implements OrderQueryRepository{

	private final JPAQueryFactory jpaQueryFactory;

	QOrder qOrder = QOrder.order;
	QOrderProduct qOrderProduct = QOrderProduct.orderProduct;
	@Override
	public List<Order> getSearchOrder(Long userId){
		return jpaQueryFactory
			.selectFrom(qOrder)
			.where(qOrder.userId.eq(userId))
			.fetch();
	}


}
