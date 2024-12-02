package com.sparta.msa_exam.order.entity;

import java.util.ArrayList;
import java.util.List;

import com.sparta.msa_exam.order.valueobject.DeliveryRequest;
import com.sparta.msa_exam.order.valueobject.OrderTotalPrice;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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
@Table(name = "p_order")
public class Order {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToMany(mappedBy = "order", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<OrderProduct> orderProducts = new ArrayList<>();

	@Embedded
	private DeliveryRequest deliveryRequest;

	@Embedded
	private OrderTotalPrice totalPrice;

	@Column(name = "user_id")
	private Long userId;

	public static Order create(DeliveryRequest deliveryRequest, Long userId){
		return builder()
			.deliveryRequest(deliveryRequest)
			.userId(userId)
			.totalPrice(OrderTotalPrice.zero())
			.build();
	}

	public void addOrderProduct(List<OrderProduct> orderProducts) {
		this.orderProducts = orderProducts;
	}
	public void updateTotalPrice() {
		int total = this.orderProducts.stream()
			.map(orderProduct -> orderProduct.getTotalPrice().getValue())
			.reduce(0, Integer::sum);

		this.totalPrice = new OrderTotalPrice(total);
	}




}
