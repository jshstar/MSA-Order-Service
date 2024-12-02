package com.sparta.msa_exam.order.repository;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.msa_exam.order.dto.OrderProductResponse;
import com.sparta.msa_exam.order.dto.OrderResponse;
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
	public List<OrderResponse> getSearchOrder(Long userId){
		List<Tuple> results = jpaQueryFactory.query()
			.select(
				qOrder.id,
				qOrder.deliveryRequest,
				qOrderProduct.productId,
				qOrderProduct.quantity,
				qOrderProduct.totalPrice
			)
			.from(qOrder)
			.join(qOrder.orderProducts, qOrderProduct)
			.where(qOrder.userId.eq(userId))
			.fetch();

		return results.stream()
			.collect(Collectors.groupingBy(
				tuple -> tuple.get(qOrder.id), // Order ID를 기준으로 그룹화
				Collectors.mapping(tuple -> new OrderProductResponse(
					tuple.get(qOrderProduct.productId),
					Objects.requireNonNull(tuple.get(qOrderProduct.quantity)).getValue(),
					Objects.requireNonNull(tuple.get(qOrderProduct.totalPrice)).getValue()
				), Collectors.toList())
			))
			.entrySet()
			.stream()
			.map(entry -> new OrderResponse(
				entry.getKey(),
				Objects.requireNonNull(results.stream()
					.filter(tuple -> Objects.equals(tuple.get(qOrder.id), entry.getKey()))
					.findFirst()
					.map(tuple -> tuple.get(qOrder.deliveryRequest))
					.orElse(null)),
				entry.getValue()
			))
			.toList();
	}


}
