package com.example.orderAssignmentSystem.repository;

import java.util.List;

import com.example.orderAssignmentSystem.model.Order;

public interface OrderRepository {
	public List<Order> findAll();

	public Order findById(int id);

	public void save(Order order);

	public void delete(int id);
}
