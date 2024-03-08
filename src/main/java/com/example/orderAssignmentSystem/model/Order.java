package com.example.orderAssignmentSystem.model;

import com.example.orderAssignmentSystem.model.enums.CategoryEnum;
import com.example.orderAssignmentSystem.model.enums.OrderStatusEnum;

public class Order {
	private String orderId;
	private CategoryEnum category;
	private String orderDescription;
	private String workerId;
	private OrderStatusEnum orderStatus;

	public Order() {

	}

	public Order(String orderId, CategoryEnum category, String orderDescription) {
		super();
		this.orderId = orderId;
		this.category = category;
		this.orderDescription = orderDescription;
	}

	public Order(String orderId, CategoryEnum category, String orderDescription, String workerId) {
		super();
		this.orderId = orderId;
		this.category = category;
		this.orderDescription = orderDescription;
		this.workerId = workerId;
	}

	public Order(String orderId, CategoryEnum category, String orderDescription, String workerId,
			OrderStatusEnum orderStatus) {
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

	public OrderStatusEnum getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(OrderStatusEnum orderStatus) {
		this.orderStatus = orderStatus;
	}

}
