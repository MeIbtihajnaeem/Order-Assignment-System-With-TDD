package com.example.orderAssignmentSystem.controller;

import com.example.orderAssignmentSystem.model.Order;
import com.example.orderAssignmentSystem.repository.OrderRepository;
import com.example.orderAssignmentSystem.view.OrderView;

public class OrderController {
	private OrderRepository orderRepository;

	private OrderView orderView;

	public OrderController(OrderRepository orderRepository, OrderView orderView) {
		this.orderRepository = orderRepository;
		this.orderView = orderView;
	}

	public void allCategories() {
		orderView.showAllOrders(orderRepository.findAll());
	}

	public void createNewOrder(Order order) {
		if (order != null) {

			Order existingOrder = orderRepository.findById(order.getOrderId());
			if (existingOrder != null) {
				orderView.showError("Order with id " + order.getOrderId() + " already exists", existingOrder);
				return;
			}
			orderRepository.save(order);
			orderView.orderAdded(order);
		}
		orderView.showError("Order cannot be null", null);
	}

	public void deleteOrder(Order order) {
		if (order != null) {
			Order existingOrder = orderRepository.findById(order.getOrderId());
			if (existingOrder != null) {
				orderRepository.delete(order.getOrderId());
				orderView.orderRemoved(order);
				return;
			}
			orderView.showErrorOrderNotFound("No order exist with id " + order.getOrderId(), existingOrder);
		} else {
			orderView.showError("Order cannot be null ", null);

		}
	}
}
