package com.example.orderAssignmentSystem.controller;

import java.util.List;
import java.util.Objects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.example.orderAssignmentSystem.model.CustomerOrder;
import com.example.orderAssignmentSystem.model.Worker;
import com.example.orderAssignmentSystem.model.enums.CategoryEnum;
import com.example.orderAssignmentSystem.model.enums.OrderStatusEnum;
import com.example.orderAssignmentSystem.repository.OrderRepository;
import com.example.orderAssignmentSystem.repository.WorkerRepository;
import com.example.orderAssignmentSystem.view.OrderView;

public class OrderController {
    private static final Logger LOGGER = LogManager.getLogger(OrderController.class);

    private OrderRepository orderRepository;

    private OrderView orderView;
    private WorkerRepository workerRepository;

    public OrderController(OrderRepository orderRepository, OrderView orderView, WorkerRepository workerRepository) {
        this.orderRepository = orderRepository;
        this.orderView = orderView;
        this.workerRepository = workerRepository;
    }

    public void allOrders() {
        orderView.showAllOrders(orderRepository.findAll());
    }

    private static final String ORDER_ERROR = "Order %s cannot be %s while creating order";
    private static final String NO_WORKER_FOUND = "No %s found with id %s";
    private static final String NULL_ERROR = "%s is null";
    private static final String ASSIGN_ERROR = "Cannot assign orders to worker with already assigned order";

    public void createNewOrder(CustomerOrder order) {
        LOGGER.info("Creating a new order");

        Objects.requireNonNull(order, String.format(NULL_ERROR, "Order"));
        validateOrder(order);

        if (order.getCategory() != CategoryEnum.PLUMBER) {
            LOGGER.error(String.format(ORDER_ERROR, "Category", order.getCategory()));
            throw new IllegalArgumentException(String.format(ORDER_ERROR, "Category", order.getCategory()));
        }
        if (order.getOrderStatus() != OrderStatusEnum.PENDING) {
            LOGGER.error(String.format(ORDER_ERROR, "Status", order.getOrderStatus()));
            throw new IllegalArgumentException(String.format(ORDER_ERROR, "Status", order.getOrderStatus()));
        }
        validateWorkerId(order);
        Worker existingWorker = workerRepository.findById(order.getWorker().getWorkerId());
        if (existingWorker == null) {
            LOGGER.error(String.format(NO_WORKER_FOUND, "Worker", order.getWorker().getWorkerId()));
            orderView.showError(String.format(NO_WORKER_FOUND, "Worker", order.getWorker().getWorkerId()), order);
            return;
        }

        if (existingWorker.getOrders() == null) {
            orderRepository.save(order);
            orderView.orderAdded(order);
        } else {
            if (!_checkOrdersStatus(existingWorker.getOrders())) {
                orderRepository.save(order);
                orderView.orderAdded(order);
            } else {
                LOGGER.error(ASSIGN_ERROR);
                orderView.showError(ASSIGN_ERROR, order);
            }
        }
    }

    private boolean _checkOrdersStatus(List<CustomerOrder> orders) {
        for (CustomerOrder order : orders) {
            if (order.getOrderStatus() == OrderStatusEnum.PENDING) {
                return true;
            }
        }
        return false;
    }

    private static final String MODIFY_ERROR = "Cannot modify order because the order remains the same";

    public void modifyOrder(CustomerOrder order) {
        LOGGER.info("Modifying an order");

        Objects.requireNonNull(order, String.format(NULL_ERROR, "Order"));
        Objects.requireNonNull(order.getOrderId(), String.format(NULL_ERROR, "Order id"));

        validateOrder(order);
        validateWorkerId(order);

        CustomerOrder oldOrder = orderRepository.findById(order.getOrderId());
        if (oldOrder == null) {
            LOGGER.error(String.format(NO_WORKER_FOUND, "Order", order.getOrderId()));
            orderView.showError(String.format(NO_WORKER_FOUND, "Order", order.getOrderId()), oldOrder);
            return;
        }
        Worker existingWorker = workerRepository.findById(order.getWorker().getWorkerId());
        if (existingWorker == null) {
            LOGGER.error(String.format(NO_WORKER_FOUND, "Worker", order.getWorker().getWorkerId()));
            orderView.showError(String.format(NO_WORKER_FOUND, "Worker", order.getWorker().getWorkerId()), order);
            return;
        }
        if (isOrderUnchanged(order, oldOrder)) {
            LOGGER.error(MODIFY_ERROR);
            orderView.showError(MODIFY_ERROR, order);
            return;
        }
        orderRepository.save(order);
        orderView.orderModified(order);
    }

    private static final String WORKER_POSITIVE_ERROR = "Worker ID must be a positive integer";

    private void validateWorkerId(CustomerOrder order) {
        Objects.requireNonNull(order.getWorker().getWorkerId(), String.format(NULL_ERROR, "Worker ID"));

        if (order.getWorker().getWorkerId() <= 0) {
            LOGGER.error(WORKER_POSITIVE_ERROR);
            throw new IllegalArgumentException(WORKER_POSITIVE_ERROR);
        }
    }

    private static final String DESCRIPTION_EMPTY_ERROR = "Order description cannot be empty";
    private static final String DESCRIPTION_LENGTH_ERROR = "Order description cannot be greater than 50 characters";

    private void validateOrder(CustomerOrder order) {
        Objects.requireNonNull(order.getCategory(), String.format(NULL_ERROR, "Category"));
        Objects.requireNonNull(order.getOrderDescription(), String.format(NULL_ERROR, "Order description"));
        Objects.requireNonNull(order.getOrderStatus(), String.format(NULL_ERROR, "Order status"));
        Objects.requireNonNull(order.getWorker(), String.format(NULL_ERROR, "Worker"));

        if (order.getOrderDescription().isEmpty()) {
            LOGGER.error(DESCRIPTION_EMPTY_ERROR);
            throw new IllegalArgumentException(DESCRIPTION_EMPTY_ERROR);
        }

        if (order.getOrderDescription().length() > 50) {
            LOGGER.error(DESCRIPTION_LENGTH_ERROR);
            throw new IllegalArgumentException(DESCRIPTION_LENGTH_ERROR);
        }
    }

    private boolean isOrderUnchanged(CustomerOrder order, CustomerOrder oldOrder) {
        return order.getCategory() == oldOrder.getCategory() && order.getOrderStatus() == oldOrder.getOrderStatus()
                && order.getWorker().getWorkerId() == oldOrder.getWorker().getWorkerId()
                && order.getOrderDescription().equals(oldOrder.getOrderDescription());
    }
}
