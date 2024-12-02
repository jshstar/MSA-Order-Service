package com.sparta.msa_exam.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sparta.msa_exam.order.entity.OrderProduct;

public interface OrderProductJpaRepository extends JpaRepository<OrderProduct, Long> {
}
