package com.example.orderAssignmentSystem.view;

import java.util.List;

import com.example.orderAssignmentSystem.model.Order;

public interface OrderView {
	void showAllOrders(List<Order> orders);

	void showError(String message, Order order);

	void orderAdded(Order order);

	void orderRemoved(Order order);

	void showErrorOrderNotFound(String message, Order order);
}
