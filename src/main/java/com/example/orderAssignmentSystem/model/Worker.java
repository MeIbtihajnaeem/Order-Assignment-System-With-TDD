package com.example.orderAssignmentSystem.model;

import java.util.List;

public class Worker {

	private String workerId;
	private String workerName;
	private List<Order> orders;

	public Worker(String workerId, String workerName) {
		super();
		this.workerId = workerId;
		this.workerName = workerName;
	}

	public Worker(String workerId, String workerName, List<Order> orders) {
		super();
		this.workerId = workerId;
		this.workerName = workerName;
		this.orders = orders;
	}

	public List<Order> getOrders() {
		return orders;
	}

	public String getWorkerId() {
		return workerId;
	}

	public String getWorkerName() {
		return workerName;
	}

}
