package com.sparta.msa_exam.order.repository;

import java.util.List;

import com.sparta.msa_exam.order.entity.Order;

public interface OrderQueryRepository {

	List<Order> getSearchOrder(Long userId);
}
