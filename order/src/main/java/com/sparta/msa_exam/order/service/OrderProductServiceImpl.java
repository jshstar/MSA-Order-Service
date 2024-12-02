package com.sparta.msa_exam.order.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sparta.msa_exam.order.client.dto.ProductResponse;
import com.sparta.msa_exam.order.common.code.OrderErrorCode;
import com.sparta.msa_exam.order.common.exception.OrderException;
import com.sparta.msa_exam.order.dto.OrderProductRequest;
import com.sparta.msa_exam.order.entity.Order;
import com.sparta.msa_exam.order.entity.OrderProduct;
import com.sparta.msa_exam.order.repository.OrderProductJpaRepository;
import com.sparta.msa_exam.order.valueobject.Quantity;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderProductServiceImpl implements OrderProductService{
	private final OrderProductJpaRepository orderProductJpaRepository;
	private final ProductService productService;

	@Override
	@Transactional
	public List<OrderProduct> createOrderProduct(List<OrderProductRequest> orderProducts, Order order){
		List<Long> productIds = orderProducts.stream().map(OrderProductRequest::getProductId).toList();
		List<ProductResponse> productResponseList = productService.getProductsByIds(productIds);

		return productResponseList.stream()
			.map(productResponse -> {
				OrderProductRequest matchingRequest = orderProducts.stream()
					.filter(orderProductRequest -> orderProductRequest.getProductId().equals(productResponse.getId()))
					.findFirst()
					.orElseThrow(() -> new OrderException(OrderErrorCode.NOT_FOUND_PRODUCT));

				Quantity quantity = new Quantity(matchingRequest.getQuantity());
				return OrderProduct.create(productResponse.getId(), quantity, productResponse.getPrice(), order);
			})
			.toList();
	}





}
