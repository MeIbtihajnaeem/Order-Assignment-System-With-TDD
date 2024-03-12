package com.example.orderAssignmentSystem.model;

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
//
//	public CustomerOrder(Long orderId, CategoryEnum category, String orderDescription) {
//		super();
//		this.orderId = orderId;
//		this.category = category;
//		this.orderDescription = orderDescription;
//	}

	public CustomerOrder(CategoryEnum category, String orderDescription, OrderStatusEnum orderStatus, Worker worker) {
		super();
		this.category = category;
		this.orderDescription = orderDescription;
		this.orderStatus = orderStatus;
		this.worker = worker;
	}

//	public CustomerOrder(Long orderId, CategoryEnum category, String orderDescription, Worker worker) {
//		super();
//		this.orderId = orderId;
//		this.category = category;
//		this.orderDescription = orderDescription;
//		this.worker = worker;
//	}
//
//	public CustomerOrder(Long orderId, CategoryEnum category, String orderDescription, Worker worker,
//			OrderStatusEnum orderStatus) {
//		super();
//		this.orderId = orderId;
//		this.category = category;
//		this.orderDescription = orderDescription;
//		this.worker = worker;
//		this.orderStatus = orderStatus;
//	}

	public CustomerOrder(Long orderId, CategoryEnum category, String orderDescription, OrderStatusEnum orderStatus,
			Worker worker) {
		this.orderId = orderId;
		this.category = category;
		this.orderDescription = orderDescription;
		this.worker = worker;
		this.orderStatus = orderStatus;
	}

	@Override
	public String toString() {
		return "CustomerOrder [orderId=" + orderId + ", category=" + category + ", orderDescription=" + orderDescription
				+ ", worker=" + worker + ", orderStatus=" + orderStatus + "]";
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

	public void setWorker(Worker worker) {
		this.worker = worker;
	}

	public void setOrderStatus(OrderStatusEnum orderStatus) {
		this.orderStatus = orderStatus;
	}

}
