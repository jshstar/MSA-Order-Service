package com.sparta.msa_exam.order.entity;

import com.sparta.msa_exam.order.valueobject.Quantity;
import com.sparta.msa_exam.order.valueobject.TotalPrice;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "p_order_product")
public class OrderProduct {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "order_id", nullable = false)
	private Order order;

	@Column(name = "product_id", nullable = false)
	private Long productId;

	@Embedded
	private Quantity quantity;

	@Embedded
	private TotalPrice totalPrice;

	public static OrderProduct create(Long productId, Quantity quantity, Integer unitPrice, Order order){
		TotalPrice totalPrice = TotalPrice.calculate(unitPrice, quantity.getValue());
		return builder()
			.productId(productId)
			.quantity(quantity)
			.order(order)
			.totalPrice(totalPrice)
			.build();
	}

	public void addOrder(Order order) {
		this.order = order;
	}

}
