package com.example.orderAssignmentSystem.model;

public class Order {
	private int orderId;
	private Category category;
	private String orderDescription;
	private Worker worker;

	public Order(int orderId, Category category, String orderDescription) {
		super();
		this.orderId = orderId;
		this.category = category;
		this.orderDescription = orderDescription;
	}

	public Order(int orderId, Category category, String orderDescription, Worker worker) {
		super();
		this.orderId = orderId;
		this.category = category;
		this.orderDescription = orderDescription;
		this.worker = worker;
	}

	public Worker getWorker() {
		return worker;
	}

	public int getOrderId() {
		return orderId;
	}

	public Category getCategory() {
		return category;
	}

	public String getOrderDescription() {
		return orderDescription;
	}

}
