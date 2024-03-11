package com.example.orderAssignmentSystem.view;

import java.util.List;

import com.example.orderAssignmentSystem.model.CustomerOrder;

public interface OrderView {
	void showAllOrders(List<CustomerOrder> orders);

	void showError(String message, CustomerOrder order);

	void orderAdded(CustomerOrder order);
	
	void orderModified(CustomerOrder order);

	void orderRemoved(CustomerOrder order);

	void showErrorOrderNotFound(String message, CustomerOrder order);
}
