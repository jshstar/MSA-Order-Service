package com.sparta.msa_exam.order.service;

import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sparta.msa_exam.order.common.code.OrderErrorCode;
import com.sparta.msa_exam.order.common.exception.OrderException;
import com.sparta.msa_exam.order.context.AuthValidationContext;
import com.sparta.msa_exam.order.dto.OrderProductResponse;
import com.sparta.msa_exam.order.dto.OrderRequest;
import com.sparta.msa_exam.order.dto.OrderResponse;
import com.sparta.msa_exam.order.dto.OrderUpdateRequest;
import com.sparta.msa_exam.order.entity.Order;
import com.sparta.msa_exam.order.entity.OrderProduct;
import com.sparta.msa_exam.order.repository.OrderJpaRepository;
import com.sparta.msa_exam.order.repository.OrderQueryRepository;
import com.sparta.msa_exam.order.valueobject.DeliveryRequest;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

	private final OrderJpaRepository orderJpaRepository;
	private final OrderProductService orderProductService;
	private final OrderQueryRepository orderQueryRepository;

	@Override
	@Transactional
	@CacheEvict(value = "orderSearchCache", key = "'orderReponse'")
	public OrderResponse createOrder(OrderRequest request) {
		DeliveryRequest deliveryRequest = new DeliveryRequest(request.getDeliveryRequest());
		Order order = Order.create(deliveryRequest, AuthValidationContext.getUserId());
		List<OrderProduct> orderProducts = orderProductService.createOrderProduct(request.getOrderProductList(), order);
		order.addOrderProduct(orderProducts);
		order.updateTotalPrice();
		return new OrderResponse(orderJpaRepository.save(order));
	}

	@Override
	@Transactional(readOnly = true)
	@Cacheable(value = "orderIdCache", key = "#orderId")
	public List<OrderProductResponse> getOrder(Long orderId) {
		Order order = findByOrder(orderId);
		return order.getOrderProducts().stream().map(
			orderProduct -> new OrderProductResponse(
			orderProduct.getProductId(),
			orderProduct.getQuantity(),
			orderProduct.getTotalPrice()
		)).toList();
	}

	@Override
	@Transactional(readOnly = true)
	@Cacheable(value = "orderSearchCache", key = "'orderReponse'")
	public List<OrderResponse> getSearchOrder() {
		Long userId = AuthValidationContext.getUserId();
		List<Order> orderList = orderQueryRepository.getSearchOrder(userId);
		return orderList.stream()
			.map(order -> new OrderResponse(
				order.getId(),
				order.getDeliveryRequest(),
				order.getTotalPrice(),
				order.getOrderProducts().stream()
					.map(OrderProduct::getProductId)
					.toList()
			))
			.toList();
	}

	@Override
	@Transactional
	@CachePut(value = "orderIdCache", key = "#orderId")
	@CacheEvict(value = "orderSearchCache", key = "'orderReponse'")
	public OrderResponse updateOrder(OrderUpdateRequest request, Long orderId) {
		Order order = findByOrder(orderId);
		validateOrderInUser(order, AuthValidationContext.getUserId());
		DeliveryRequest deliveryRequest = new DeliveryRequest(request.getDeliveryRequest());
		order.updateDeliveryRequest(deliveryRequest);

		return new OrderResponse(orderJpaRepository.save(order));
	}

	private void validateOrderInUser(Order order, Long userId) {
		if (!order.getUserId().equals(userId)) {
			throw new IllegalArgumentException(new OrderException(OrderErrorCode.NOT_FOUND_ORDER));
		}
	}

	private Order findByOrder(Long orderId) {
		return orderJpaRepository.findById(orderId)
			.orElseThrow(() -> new IllegalArgumentException(new OrderException(OrderErrorCode.NOT_FOUND_ORDER)));
	}

}
