package com.example.orderAssignmentSystem.model;

import com.example.orderAssignmentSystem.model.enums.OrderStatus;

public class Order {
	private String orderId;
	private Category category;
	private String orderDescription;
	private String workerId;
	private OrderStatus orderStatus;

	public Order() {

	}

	public Order(String orderId, Category category, String orderDescription) {
		super();
		this.orderId = orderId;
		this.category = category;
		this.orderDescription = orderDescription;
	}

	public Order(String orderId, Category category, String orderDescription, String workerId) {
		super();
		this.orderId = orderId;
		this.category = category;
		this.orderDescription = orderDescription;
		this.workerId = workerId;
	}

	public Order(String orderId, Category category, String orderDescription, String workerId, OrderStatus orderStatus) {
		super();
		this.orderId = orderId;
		this.category = category;
		this.orderDescription = orderDescription;
		this.workerId = workerId;
		this.orderStatus = orderStatus;
	}

	public String getWorkerId() {
		return workerId;
	}

	public String getOrderId() {
		return orderId;
	}

	public OrderStatus getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(OrderStatus orderStatus) {
		this.orderStatus = orderStatus;
	}

}
