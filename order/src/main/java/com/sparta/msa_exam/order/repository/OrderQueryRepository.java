package com.sparta.msa_exam.order.repository;

import java.util.List;

import com.sparta.msa_exam.order.dto.OrderResponse;

public interface OrderQueryRepository {

	List<OrderResponse> getSearchOrder(Long userId);
}
