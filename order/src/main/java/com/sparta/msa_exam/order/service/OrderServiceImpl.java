package com.sparta.msa_exam.order.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sparta.msa_exam.order.common.code.OrderErrorCode;
import com.sparta.msa_exam.order.common.exception.OrderException;
import com.sparta.msa_exam.order.context.AuthValidationContext;
import com.sparta.msa_exam.order.dto.OrderRequest;
import com.sparta.msa_exam.order.dto.OrderResponse;
import com.sparta.msa_exam.order.entity.Order;
import com.sparta.msa_exam.order.entity.OrderProduct;
import com.sparta.msa_exam.order.repository.OrderJpaRepository;
import com.sparta.msa_exam.order.repository.OrderQueryRepository;
import com.sparta.msa_exam.order.valueobject.DeliveryRequest;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService{

	private final OrderJpaRepository orderJpaRepository;
	private final OrderProductService orderProductService;
	private final OrderQueryRepository orderQueryRepository;

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

	@Override
	@Transactional(readOnly = true)
	public OrderResponse getOrder(Long orderId){
		Order order = orderJpaRepository.findById(orderId)
			.orElseThrow(() -> new IllegalArgumentException(new OrderException(OrderErrorCode.NOT_FOUND_ORDER)));
		return new OrderResponse(order);
	}

	@Override
	@Transactional(readOnly = true)
	public List<OrderResponse> getSearchOrder(){
		return orderQueryRepository.getSearchOrder(AuthValidationContext.getUserId());
	}


}
