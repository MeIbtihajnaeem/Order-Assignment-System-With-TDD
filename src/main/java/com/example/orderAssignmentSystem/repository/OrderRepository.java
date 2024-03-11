package com.example.orderAssignmentSystem.repository;

import java.util.List;

import com.example.orderAssignmentSystem.model.CustomerOrder;

public interface OrderRepository {
	public List<CustomerOrder> findAll();

	public CustomerOrder findById(Long id);

	public CustomerOrder save(CustomerOrder order);

	public void delete(CustomerOrder order);
}
