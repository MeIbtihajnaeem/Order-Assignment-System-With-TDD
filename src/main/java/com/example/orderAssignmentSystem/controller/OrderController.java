package com.example.orderAssignmentSystem.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.example.orderAssignmentSystem.model.Order;
import com.example.orderAssignmentSystem.model.Worker;
import com.example.orderAssignmentSystem.model.enums.OrderStatusEnum;
import com.example.orderAssignmentSystem.repository.OrderRepository;
import com.example.orderAssignmentSystem.repository.WorkerRepository;
import com.example.orderAssignmentSystem.view.OrderView;

public class OrderController {
	private static final Logger LOGGER = LogManager.getLogger(WorkerController.class);

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
			LOGGER.error("ERROR: Order cannot be null (at createNewOrder method)");

			throw new NullPointerException("Order cannot be null");
		}
		Order existingOrder = orderRepository.findById(order.getOrderId());
		if (existingOrder != null) {
			LOGGER.info("INFO: Order with " + order.getOrderId() + " found  (at createNewOrder method)");
			LOGGER.error("ERROR: Therefore new order with this id cannot be created!  (at createNewOrder method)");
			orderView.showError("Order with id " + order.getOrderId() + " already exists", existingOrder);
			return;
		}

		String workerId = order.getWorkerId();
		if (workerId != null) {
			Worker worker = workerRepository.findById(workerId);
			if (worker != null) {
				LOGGER.info("INFO: Worker with " + worker.getWorkerId() + " found  (at createNewOrder method)");

				order.setOrderStatus(OrderStatusEnum.PENDING);
				LOGGER.info("INFO: OrderStatus is set to Pending (at createNewOrder method)");

				orderRepository.save(order);
				LOGGER.info("INFO: Order is sucessfully created! (at createNewOrder method)");
				orderView.orderAdded(order);
			} else {
				LOGGER.error("ERROR: Worker not found (at createNewOrder method)");
				orderView.showError("Worker not found", null);
			}
		} else {
			LOGGER.error("ERROR: WorkerId cannot be null (at createNewOrder method)");
			orderView.showError("Cannot create Order without Worker", null);
		}

	}

	public void deleteOrder(Order order) {
		if (order == null) {
			LOGGER.error("ERROR: Order cannot be null (at deleteOrder method)");

			throw new NullPointerException("Order cannot be null");
		}
		Order existingOrder = orderRepository.findById(order.getOrderId());
		if (existingOrder != null) {
			LOGGER.info("INFO: Order with " + order.getOrderId() + " found  (at deleteOrder method)");

			orderRepository.delete(order.getOrderId());
			LOGGER.debug("DEBUG: Order is sucessfully deleted! (at deleteOrder method)");

			orderView.orderRemoved(order);
			return;
		}
		LOGGER.error("ERROR: No order exist with id" + order.getOrderId() + " (at deleteOrder method)");

		orderView.showErrorOrderNotFound("No order exist with id " + order.getOrderId(), existingOrder);

	}

	public void modifyOrderStatus(Order order, OrderStatusEnum status) {
		if (order == null) {
			LOGGER.error("ERROR: Order cannot be null (at modifyOrderStatus method)");

			throw new NullPointerException("Order cannot be null");
		}
		if (status == null) {
			LOGGER.error("ERROR: Status cannot be null (at modifyOrderStatus method)");
			throw new NullPointerException("Status cannot be null");
		}

		Order existingOrder = orderRepository.findById(order.getOrderId());

		if (existingOrder == null) {
			LOGGER.error("ERROR: No order exist with id " + order.getOrderId() + " (at modifyOrderStatus method)");
			orderView.showErrorOrderNotFound("No order exist with id " + order.getOrderId(), existingOrder);
			return;

		} else {
			if (existingOrder.getOrderStatus() == status) {
				LOGGER.info(
						"INFO: No modification occore since order status is not changed (at modifyOrderStatus method)");
				return;
			}

			order.setOrderStatus(status);
			orderRepository.update(order);
			LOGGER.debug("DEBUG: Order is sucessfully modified! (at modifyOrderStatus method)");
			orderView.orderModified(order);

		}

	}

}
