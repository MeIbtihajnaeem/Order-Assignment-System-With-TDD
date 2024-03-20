package com.example.orderAssignmentSystem.model;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import com.example.orderAssignmentSystem.model.enums.CategoryEnum;
import com.example.orderAssignmentSystem.model.enums.OrderStatusEnum;

@Entity
public class CustomerOrder {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long orderId;

	@Column(name = "order_category")
	@Enumerated(EnumType.STRING)
	private CategoryEnum category;

	@Column(name = "description")
	private String orderDescription;

	@OneToOne
	private Worker worker;

	@Column(name = "order_status")
	@Enumerated(EnumType.STRING)
	private OrderStatusEnum orderStatus;

	public CustomerOrder() {

	}

	public CustomerOrder(CategoryEnum category, String orderDescription, OrderStatusEnum orderStatus, Worker worker) {
		super();
		this.category = category;
		this.orderDescription = orderDescription;
		this.orderStatus = orderStatus;
		this.worker = worker;
	}

	public CustomerOrder(Long orderId, CategoryEnum category, String orderDescription, OrderStatusEnum orderStatus,
			Worker worker) {
		this.orderId = orderId;
		this.category = category;
		this.orderDescription = orderDescription;
		this.worker = worker;
		this.orderStatus = orderStatus;
	}

	public Long getOrderId() {
		return orderId;
	}

	public CategoryEnum getCategory() {
		return category;
	}

	public String getOrderDescription() {
		return orderDescription;
	}

	public Worker getWorker() {
		return worker;
	}

	public OrderStatusEnum getOrderStatus() {
		return orderStatus;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public void setCategory(CategoryEnum category) {
		this.category = category;
	}

	public void setOrderDescription(String orderDescription) {
		this.orderDescription = orderDescription;
	}

	public void setOrderStatus(OrderStatusEnum orderStatus) {
		this.orderStatus = orderStatus;
	}

	@Override
	public String toString() {
		return "CustomerOrder [category=" + category + ", orderDescription=" + orderDescription + ", worker=" + worker
				+ ", orderStatus=" + orderStatus + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(orderId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CustomerOrder other = (CustomerOrder) obj;
		return Objects.equals(orderId, other.orderId);
	}

}
