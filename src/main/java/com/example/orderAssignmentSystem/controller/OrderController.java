package com.example.orderAssignmentSystem.controller;

import com.example.orderAssignmentSystem.model.Order;
import com.example.orderAssignmentSystem.model.Worker;
import com.example.orderAssignmentSystem.model.enums.OrderStatus;
import com.example.orderAssignmentSystem.repository.OrderRepository;
import com.example.orderAssignmentSystem.repository.WorkerRepository;
import com.example.orderAssignmentSystem.view.OrderView;

public class OrderController {
	private OrderRepository orderRepository;

	private OrderView orderView;
	private WorkerRepository workerRepository;

	public OrderController(OrderRepository orderRepository, OrderView orderView, WorkerRepository workerRepository) {
		this.orderRepository = orderRepository;
		this.orderView = orderView;
		this.workerRepository = workerRepository;
	}

	public void allCategories() {
		orderView.showAllOrders(orderRepository.findAll());
	}

	public void createNewOrder(Order order) {
		if (order == null) {
			throw new NullPointerException("Order cannot be null");
		}
		Order existingOrder = orderRepository.findById(order.getOrderId());
		if (existingOrder != null) {
			orderView.showError("Order with id " + order.getOrderId() + " already exists", existingOrder);
			return;
		}

		String workerId = order.getWorkerId();
		if (workerId != null) {
			Worker worker = workerRepository.findById(workerId);
			if (worker != null) {
				order.setOrderStatus(OrderStatus.PENDING);
				orderRepository.save(order);

				orderView.orderAdded(order);
			} else {
				orderView.showError("Worker not found", null);
			}
		} else {
			orderView.showError("Cannot create Order without Worker", null);
		}

	}

	public void deleteOrder(Order order) {
		if (order == null) {
			throw new NullPointerException("Order cannot be null");
		}
		Order existingOrder = orderRepository.findById(order.getOrderId());
		if (existingOrder != null) {
			orderRepository.delete(order.getOrderId());
			orderView.orderRemoved(order);
			return;
		}
		orderView.showErrorOrderNotFound("No order exist with id " + order.getOrderId(), existingOrder);

	}

	public void modifyOrderStatus(Order order, OrderStatus status) {
		if (order == null) {
			throw new NullPointerException("Order cannot be null");
		}
		if (status == null) {
			throw new NullPointerException("Status cannot be null");
		}

		Order existingOrder = orderRepository.findById(order.getOrderId());

		if (existingOrder == null) {
			orderView.showErrorOrderNotFound("No order exist with id " + order.getOrderId(), existingOrder);
			return;

		} else {
			if (existingOrder.getOrderStatus() == status) {
				return;
			}

			order.setOrderStatus(status);
			orderRepository.update(order);
			orderView.orderModified(order);

		}

	}

}
