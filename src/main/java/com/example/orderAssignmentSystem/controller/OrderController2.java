//package com.example.orderAssignmentSystem.controller;
//
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//
//import com.example.orderAssignmentSystem.model.CustomerOrder;
//import com.example.orderAssignmentSystem.model.Worker;
//import com.example.orderAssignmentSystem.model.enums.OrderStatusEnum;
//import com.example.orderAssignmentSystem.repository.OrderRepository;
//import com.example.orderAssignmentSystem.repository.WorkerRepository;
//import com.example.orderAssignmentSystem.view.OrderView;
//
//public class OrderController2 {
//	private static final Logger LOGGER = LogManager.getLogger(WorkerController.class);
//
//	private OrderRepository orderRepository;
//
//	private OrderView orderView;
//	private WorkerRepository workerRepository;
//
//	public OrderController(OrderRepository orderRepository, OrderView orderView, WorkerRepository workerRepository) {
//		this.orderRepository = orderRepository;
//		this.orderView = orderView;
//		this.workerRepository = workerRepository;
//	}
//
//	public void allOrders() {
//		orderView.showAllOrders(orderRepository.findAll());
//	}
//
//	public void createNewOrder(CustomerOrder order) {
//		if (order == null) {
//			LOGGER.error("ERROR: Order cannot be null (at createNewOrder method)");
//
//			throw new NullPointerException("Order cannot be null");
//		}
//		if (order.getOrderStatus() == null) {
//			LOGGER.error("ERROR: Order status cannot be null (at createNewOrder method)");
//
//			throw new NullPointerException("Order status cannot be null");
//		}
//		if (order.getWorker() == null) {
//			LOGGER.error("ERROR: Worker cannot be null (at createNewOrder method)");
//
//			throw new NullPointerException("Worker cannot be null");
//		}
//		Worker worker = order.getWorker();
//		Worker existingWorker = workerRepository.findById(worker.getWorkerId());
//		if (existingWorker != null) {
//			LOGGER.info("INFO: Worker with " + existingWorker.getWorkerId() + " found  (at createNewOrder method)");
//
//			order.setOrderStatus(OrderStatusEnum.PENDING);
//			LOGGER.info("INFO: OrderStatus is set to Pending (at createNewOrder method)");
//
//			CustomerOrder newOrder = orderRepository.save(order);
//			LOGGER.info("INFO: Order is sucessfully created! (at createNewOrder method)");
//			orderView.orderAdded(newOrder);
//		} else {
//			LOGGER.error("ERROR: Worker not found (at createNewOrder method)");
//			orderView.showError("Worker not found", null);
//		}
//	}
//
////	public void createNewOrder(CustomerOrder order) {
////		if (order == null) {
////			LOGGER.error("ERROR: Order cannot be null (at createNewOrder method)");
////
////			throw new NullPointerException("Order cannot be null");
////		}
////		if (order.getOrderId() != null) {
////
////			CustomerOrder existingOrder = orderRepository.findById(order.getOrderId());
////			if (existingOrder != null) {
////				LOGGER.info("INFO: Order with " + order.getOrderId() + " found  (at createNewOrder method)");
////				LOGGER.error("ERROR: Therefore new order with this id cannot be created!  (at createNewOrder method)");
////				orderView.showError("Order with id " + order.getOrderId() + " already exists", existingOrder);
////				return;
////			}
////		}
////		Worker worker = order.getWorker();
////		if (worker != null) {
////			Worker existingWorker = workerRepository.findById(worker.getWorkerId());
////			if (existingWorker != null) {
////				LOGGER.info("INFO: Worker with " + existingWorker.getWorkerId() + " found  (at createNewOrder method)");
////
////				order.setOrderStatus(OrderStatusEnum.PENDING);
////				LOGGER.info("INFO: OrderStatus is set to Pending (at createNewOrder method)");
////
////				CustomerOrder newOrder = orderRepository.save(order);
////				LOGGER.info("INFO: Order is sucessfully created! (at createNewOrder method)");
////				orderView.orderAdded(newOrder);
////			} else {
////				LOGGER.error("ERROR: Worker not found (at createNewOrder method)");
////				orderView.showError("Worker not found", null);
////			}
////		} else {
////			LOGGER.error("ERROR: WorkerId cannot be null (at createNewOrder method)");
////			orderView.showError("Cannot create Order without Worker", null);
////		}
////
////	}
//
//	public void deleteOrder(Long orderId) {
//		if (orderId == null) {
//			LOGGER.error("ERROR: Order cannot be null (at deleteOrder method)");
//
//			throw new NullPointerException("Order cannot be null");
//		}
//		CustomerOrder existingOrder = orderRepository.findById(orderId);
//		if (existingOrder != null) {
//			LOGGER.info("INFO: Order with " + existingOrder.getOrderId() + " found  (at deleteOrder method)");
//
//			orderRepository.delete(existingOrder);
//			LOGGER.debug("DEBUG: Order is sucessfully deleted! (at deleteOrder method)");
//
//			orderView.orderRemoved(existingOrder);
//			return;
//		}
//		LOGGER.error("ERROR: No order exist with id" + orderId + " (at deleteOrder method)");
//
//		orderView.showErrorOrderNotFound("No order exist with id " + orderId, existingOrder);
//
//	}
//
//	public void modifyOrderStatus(CustomerOrder order, OrderStatusEnum status) {
//		if (order == null) {
//			LOGGER.error("ERROR: Order cannot be null (at modifyOrderStatus method)");
//
//			throw new NullPointerException("Order cannot be null");
//		}
//		if (status == null) {
//			LOGGER.error("ERROR: Status cannot be null (at modifyOrderStatus method)");
//			throw new NullPointerException("Status cannot be null");
//		}
//
//		CustomerOrder existingOrder = orderRepository.findById(order.getOrderId());
//
//		if (existingOrder == null) {
//			LOGGER.error("ERROR: No order exist with id " + order.getOrderId() + " (at modifyOrderStatus method)");
//			orderView.showErrorOrderNotFound("No order exist with id " + order.getOrderId(), existingOrder);
//			return;
//
//		} else {
//			if (existingOrder.getOrderStatus() == status) {
//				LOGGER.info(
//						"INFO: No modification occore since order status is not changed (at modifyOrderStatus method)");
//				return;
//			}
//
//			order.setOrderStatus(status);
//			orderRepository.save(order);
//			LOGGER.debug("DEBUG: Order is sucessfully modified! (at modifyOrderStatus method)");
//			orderView.orderModified(order);
//
//		}
//
//	}
//
//}
