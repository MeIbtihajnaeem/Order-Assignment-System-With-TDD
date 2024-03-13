package com.example.orderAssignmentSystem.model;

import java.util.List;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
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
	@Column(name = "codiceFiscale")
	private String codiceFiscale;

	@OneToMany
	@JoinTable(name = "Worker_Order", joinColumns = { @JoinColumn(name = "ord_id") }, inverseJoinColumns = {
			@JoinColumn(name = "work_id") })
	private List<CustomerOrder> orders;

	public Worker() {

	}

	public Worker(String workerName, String codiceFiscale) {
		this.workerName = workerName;
		this.codiceFiscale = codiceFiscale;
	}

	public Long getWorkerId() {
		return workerId;
	}

	public void setWorkerId(Long workerId) {
		this.workerId = workerId;
	}

	public String getWorkerName() {
		return workerName;
	}

	public void setWorkerName(String workerName) {
		this.workerName = workerName;
	}

	public String getCodiceFiscale() {
		return codiceFiscale;
	}

	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}

	public List<CustomerOrder> getOrders() {
		return orders;
	}

	public void setOrders(List<CustomerOrder> orders) {
		this.orders = orders;
	}

	@Override
	public String toString() {
		return "Worker [workerName=" + workerName + ", codiceFiscale=" + codiceFiscale + ", orders=" + orders + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(codiceFiscale, orders, workerName);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Worker other = (Worker) obj;
		return Objects.equals(codiceFiscale, other.codiceFiscale) && Objects.equals(orders, other.orders)
				&& Objects.equals(workerName, other.workerName);
	}
	

}
