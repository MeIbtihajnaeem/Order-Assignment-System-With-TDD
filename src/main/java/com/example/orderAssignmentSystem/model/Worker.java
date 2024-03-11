package com.example.orderAssignmentSystem.model;

import java.util.List;
import javax.persistence.Entity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;

@Entity
public class Worker {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long workerId;
	@Column(name = "name")
	private String workerName;

	@OneToMany
	@JoinTable(name = "Worker_Order", joinColumns = { @JoinColumn(name = "ord_id") }, inverseJoinColumns = {
			@JoinColumn(name = "work_id") })
	private List<CustomerOrder> orders;

	public Worker() {

	}

	public Worker(Long workerId, String workerName) {
		super();
		this.workerId = workerId;
		this.workerName = workerName;
	}

	public Worker(String workerName) {
		this.workerName = workerName;
	}

	public Worker(Long workerId, String workerName, List<CustomerOrder> orders) {
		super();
		this.workerId = workerId;
		this.workerName = workerName;
		this.orders = orders;
	}

	public List<CustomerOrder> getOrders() {
		return orders;
	}

	public Long getWorkerId() {
		return workerId;
	}

	public void setOrders(List<CustomerOrder> orders) {
		this.orders = orders;
	}

}
