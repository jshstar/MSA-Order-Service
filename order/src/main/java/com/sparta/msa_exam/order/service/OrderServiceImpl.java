package com.sparta.msa_exam.order.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sparta.msa_exam.order.context.AuthValidationContext;
import com.sparta.msa_exam.order.dto.OrderRequest;
import com.sparta.msa_exam.order.dto.OrderResponse;
import com.sparta.msa_exam.order.entity.Order;
import com.sparta.msa_exam.order.entity.OrderProduct;
import com.sparta.msa_exam.order.repository.OrderJpaRepository;
import com.sparta.msa_exam.order.valueobject.DeliveryRequest;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService{

	private final OrderJpaRepository orderJpaRepository;
	private final OrderProductService orderProductService;

	@Override
	@Transactional
	public OrderResponse createOrder(OrderRequest request){
		DeliveryRequest deliveryRequest = new DeliveryRequest(request.getDeliveryRequest());
		Order order = Order.create(deliveryRequest, AuthValidationContext.getUserId());
		List<OrderProduct> orderProducts = orderProductService.createOrderProduct(request.getOrderProductList(), order);
		order.addOrderProduct(orderProducts);
		order.updateTotalPrice();
		return new OrderResponse(orderJpaRepository.save(order));
	}
}
