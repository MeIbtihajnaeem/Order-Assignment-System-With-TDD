package com.example.orderAssignmentSystem.controller;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.ignoreStubs;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.orderAssignmentSystem.model.CustomerOrder;
import com.example.orderAssignmentSystem.model.Worker;
import com.example.orderAssignmentSystem.model.enums.CategoryEnum;
import com.example.orderAssignmentSystem.model.enums.OrderStatusEnum;
import com.example.orderAssignmentSystem.repository.OrderRepository;
import com.example.orderAssignmentSystem.repository.WorkerRepository;
import com.example.orderAssignmentSystem.view.OrderView;

public class OrderControllerTest {
	@Mock
	private OrderRepository orderRepository;

	@Mock
	private OrderView orderView;

	@Mock
	private WorkerRepository workerRepository;

	@InjectMocks
	private OrderController orderController;

	private AutoCloseable closeable;

	@Before
	public void setup() {
		closeable = MockitoAnnotations.openMocks(this);
	}

	@After
	public void releaseMocks() throws Exception {
		closeable.close();
	}

	// For Valid Order List
	@Test
	public void testAllOrdersMethodWhenCustomer() {
		List<CustomerOrder> orders = asList(new CustomerOrder());
		when(orderRepository.findAll()).thenReturn(orders);
		orderController.allOrders();
		verify(orderView).showAllOrders(orders);
	}

	// For Empty Order List
	@Test
	public void testAllOrdersMethodWhenEmptyList() {
		when(orderRepository.findAll()).thenReturn(Collections.emptyList());
		orderController.allOrders();
		verify(orderView).showAllOrders(Collections.emptyList());
	}

	// For Null Order List
	@Test
	public void testAllOrdersMethodWhenNullList() {
		when(orderRepository.findAll()).thenReturn(null);
		orderController.allOrders();
		verify(orderView).showAllOrders(null);
	}

	// For Null Orders
	@Test
	public void testCreateNewOrderMethodWhenNullOrders() {
		try {
			orderController.createNewOrder(null);
			fail("Expected an NullPointerException to be thrown ");
		} catch (NullPointerException e) {
			assertEquals("Order is null", e.getMessage());
		}

	}

	// Order id should be null while creating new order
	@Test
	public void testCreateNewOrderMethodWhenOrderIdNotNull() {
		CustomerOrder order = new CustomerOrder();
		order.setOrderId(1l);
		try {
			orderController.createNewOrder(order);
			fail("Expected an IllegalArgumentException to be thrown ");
		} catch (IllegalArgumentException e) {
			assertEquals("Order id is not null", e.getMessage());
		}

	}

	// Order Category is null
	@Test
	public void testCreateNewOrderMethodWhenOrderCategoryIsNull() {
		CustomerOrder order = new CustomerOrder();
		try {
			orderController.createNewOrder(order);
			fail("Expected an NullPointerException to be thrown ");
		} catch (NullPointerException e) {
			assertEquals("Category is null", e.getMessage());
		}

	}

	// Order Description is null
	@Test
	public void testCreateNewOrderMethodWhenOrderDescriptionIsNull() {
		CustomerOrder order = new CustomerOrder();
		order.setCategory(CategoryEnum.PLUMBER);
		try {
			orderController.createNewOrder(order);
			fail("Expected an NullPointerException to be thrown ");
		} catch (NullPointerException e) {
			assertEquals("Order description is null", e.getMessage());
		}

	}
	// Order Status is null

	@Test
	public void testCreateNewOrderMethodWhenOrderStatusIsNull() {
		CustomerOrder order = new CustomerOrder();
		order.setCategory(CategoryEnum.PLUMBER);
		order.setOrderDescription("testing description");
		try {
			orderController.createNewOrder(order);
			fail("Expected an NullPointerException to be thrown ");
		} catch (NullPointerException e) {
			assertEquals("Order status is null", e.getMessage());
		}

	}

	@Test
	public void testCreateNewOrderMethodWhenOrderWorkerIsNull() {
		CustomerOrder order = new CustomerOrder();
		order.setCategory(CategoryEnum.PLUMBER);
		order.setOrderDescription("testing description");
		order.setOrderStatus(OrderStatusEnum.PENDING);

		try {
			orderController.createNewOrder(order);
			fail("Expected an NullPointerException to be thrown ");
		} catch (NullPointerException e) {
			assertEquals("Worker is null", e.getMessage());
		}

	}

	// When description is empty String
	@Test
	public void testCreateNewOrderMethodWhenOrderDescriptionIsEmptyString() {
		CustomerOrder order = new CustomerOrder(CategoryEnum.PLUMBER, "", OrderStatusEnum.PENDING, new Worker());
		try {
			orderController.createNewOrder(order);
			fail("Expected an IllegalArgumentException to be thrown ");
		} catch (IllegalArgumentException e) {
			assertEquals("Order description cannot be empty", e.getMessage());
		}

	}
	// When description is greater then 50

	@Test
	public void testCreateNewOrderMethodWhenOrderDescriptionIsGreaterThen50Charachters() {
		// 62 length string
		final String orderDescription = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		CustomerOrder order = new CustomerOrder(CategoryEnum.PLUMBER, orderDescription, OrderStatusEnum.PENDING, new Worker());
		try {
			orderController.createNewOrder(order);
			fail("Expected an IllegalArgumentException to be thrown ");
		} catch (IllegalArgumentException e) {
			assertEquals("Order description cannot be greater than 50 characters", e.getMessage());
		}

	}
	// When description is equal to 50

	@Test
	public void testCreateNewOrderMethodWhenOrderDescriptionIsExactly50Characters() {
		// 50 length string
		final String orderDescription = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWX";
		OrderStatusEnum status = OrderStatusEnum.PENDING;
		Long workerId = 1l;
		Worker worker = new Worker();
		worker.setWorkerId(workerId);
		CustomerOrder order = new CustomerOrder(CategoryEnum.PLUMBER, orderDescription, status, worker);
		try {
			orderController.createNewOrder(order);
		} catch (IllegalArgumentException e) {
			fail("No IllegalArgumentException should be thrown for exactly 50 characters description");
		}
	}

	@Test
	public void testCreateNewOrderMethodWhenOrderStatusIsNotPending() {

		final String orderDescription = "abcdefghijklmnopqrstuvwxyzasdf123";
		OrderStatusEnum status = OrderStatusEnum.COMPLETED;
		CustomerOrder order = new CustomerOrder(CategoryEnum.PLUMBER, orderDescription, status, new Worker());
		try {
			orderController.createNewOrder(order);
			fail("Expected an IllegalArgumentException to be thrown ");
		} catch (IllegalArgumentException e) {
			assertEquals("Order Status cannot be " + status + " while creating order", e.getMessage());
		}
	}

	@Test
	public void testCreateNewOrderMethodWhenWorkerIdIsNull() {

		final String orderDescription = "abcdefghijklmnopqrstuvwxyzasdf123";
		OrderStatusEnum status = OrderStatusEnum.PENDING;
		Worker worker = new Worker();
		CustomerOrder order = new CustomerOrder(CategoryEnum.PLUMBER, orderDescription, status, worker);
		try {
			orderController.createNewOrder(order);
			fail("Expected an NullPointerException to be thrown ");
		} catch (NullPointerException e) {
			assertEquals("Worker ID is null", e.getMessage());
		}
	}

	@Test
	public void testCreateNewOrderMethodWhenWorkerIdIsZero() {
		final String orderDescription = "abcdefghijklmnopqrstuvwxyzasdf123";
		OrderStatusEnum status = OrderStatusEnum.PENDING;
		Worker worker = new Worker();
		worker.setWorkerId(0l);
		CustomerOrder order = new CustomerOrder(CategoryEnum.PLUMBER, orderDescription, status, worker);
		try {
			orderController.createNewOrder(order);
			fail("Expected an IllegalArgumentException to be thrown ");
		} catch (IllegalArgumentException e) {
			assertEquals("Worker ID must be a positive integer", e.getMessage());
		}
	}

	@Test
	public void testCreateNewOrderMethodWhenWorkerIdIsNegative() {
		final String orderDescription = "abcdefghijklmnopqrstuvwxyzasdf123";
		OrderStatusEnum status = OrderStatusEnum.PENDING;
		Worker worker = new Worker();
		worker.setWorkerId(-1l);
		CustomerOrder order = new CustomerOrder(CategoryEnum.PLUMBER, orderDescription, status, worker);
		try {
			orderController.createNewOrder(order);
			fail("Expected an IllegalArgumentException to be thrown ");
		} catch (IllegalArgumentException e) {
			assertEquals("Worker ID must be a positive integer", e.getMessage());
		}
	}

	@Test
	public void testCreateNewOrderMethodWhenWorkerNotFound() {
		final String orderDescription = "abcdefghijklmnopqrstuvwxyzasdf123";
		OrderStatusEnum status = OrderStatusEnum.PENDING;
		Long workerId = 1l;
		Worker worker = new Worker();
		worker.setWorkerId(workerId);
		CustomerOrder order = new CustomerOrder(CategoryEnum.PLUMBER, orderDescription, status, worker);
		when(workerRepository.findById(workerId)).thenReturn(null);
		orderController.createNewOrder(order);
		InOrder inOrder = inOrder(orderRepository, orderView, workerRepository);
		inOrder.verify(orderView).showError("No Worker found with id " + workerId, order);
		verifyNoMoreInteractions(ignoreStubs(orderRepository));
	}

	@Test
	public void testCreateNewOrderMethodWhenWorkerFoundButWithDifferentCategory() {
		final String orderDescription = "abcdefghijklmnopqrstuvwxyzasdf123";
		OrderStatusEnum status = OrderStatusEnum.PENDING;
		CategoryEnum workerCategory = CategoryEnum.PLUMBER;
		CategoryEnum orderCategory = CategoryEnum.ELECTRICIAN;
		Long workerId = 1l;
		Worker worker = new Worker();
		worker.setWorkerId(workerId);
		worker.setCategory(workerCategory);
		CustomerOrder order = new CustomerOrder(orderCategory, orderDescription, status, worker);
		when(workerRepository.findById(workerId)).thenReturn(worker);
		orderController.createNewOrder(order);
		InOrder inOrder = inOrder(orderRepository, orderView, workerRepository);
		inOrder.verify(orderView).showError("Cannot assign orders to this worker because it is of different category",
				order);
		verifyNoMoreInteractions(ignoreStubs(orderRepository));
	}

	@Test
	public void testCreateNewOrderMethodWhenWorkerFoundButAlreadyAssignedPendingOrder() {
		final String orderDescription = "abcdefghijklmnopqrstuvwxyzasdf123";
		OrderStatusEnum status = OrderStatusEnum.PENDING;
		Long workerId = 1l;
		Long orderId = 1l;
		Worker worker = new Worker();
		worker.setWorkerId(workerId);
		worker.setCategory(CategoryEnum.PLUMBER);

		CustomerOrder previousPendingOrder = new CustomerOrder(CategoryEnum.PLUMBER, orderDescription, status, worker);
		previousPendingOrder.setOrderId(orderId);
		worker.setOrders(asList(previousPendingOrder));
		CustomerOrder order = new CustomerOrder(CategoryEnum.PLUMBER, orderDescription, status, worker);
		when(workerRepository.findById(workerId)).thenReturn(worker);
		orderController.createNewOrder(order);
		InOrder inOrder = inOrder(orderRepository, orderView, workerRepository);
		inOrder.verify(orderView).showError("Cannot assign orders to worker with already assigned order", order);
		verifyNoMoreInteractions(ignoreStubs(orderRepository));
	}

	@Test
	public void testCreateNewOrderMethodWhenWorkerFoundAndNoPendingOrders() {
		final String orderDescription = "abcdefghijklmnopqrstuvwxyzasdf123";
		OrderStatusEnum status = OrderStatusEnum.PENDING;
		Long workerId = 1l;
		Long orderId = 1l;
		Worker worker = new Worker();
		worker.setWorkerId(workerId);
		worker.setCategory(CategoryEnum.PLUMBER);

		CustomerOrder previousPendingOrder = new CustomerOrder(CategoryEnum.PLUMBER, orderDescription, OrderStatusEnum.COMPLETED,
				worker);
		previousPendingOrder.setOrderId(orderId);
		worker.setOrders(asList(previousPendingOrder));
		CustomerOrder order = new CustomerOrder(CategoryEnum.PLUMBER, orderDescription, status, worker);
		when(workerRepository.findById(workerId)).thenReturn(worker);
		orderController.createNewOrder(order);
		InOrder inOrder = inOrder(orderRepository, orderView, workerRepository);
		inOrder.verify(orderRepository).save(order);
		inOrder.verify(orderView).orderAdded(order);
		verifyNoMoreInteractions(ignoreStubs(orderRepository));
	}

	@Test
	public void testCreateNewOrderMethod() {
		final String orderDescription = "abcdefghijklmnopqrstuvwxyzasdf123";
		OrderStatusEnum status = OrderStatusEnum.PENDING;
		Long workerId = 1l;
		Worker worker = new Worker();
		worker.setWorkerId(workerId);
		worker.setCategory(CategoryEnum.PLUMBER);
		CustomerOrder order = new CustomerOrder(CategoryEnum.PLUMBER, orderDescription, status, worker);
		when(workerRepository.findById(workerId)).thenReturn(worker);
		orderController.createNewOrder(order);
		InOrder inOrder = inOrder(orderRepository, orderView, workerRepository);
		inOrder.verify(orderRepository).save(order);
		inOrder.verify(orderView).orderAdded(order);
		verifyNoMoreInteractions(ignoreStubs(orderRepository));
	}

	@Test
	public void testModifyOrderMethodWhenNullOrders() {

		try {
			orderController.modifyOrder(null);
			fail("Expected an NullPointerException to be thrown ");
		} catch (NullPointerException e) {
			assertEquals("Order is null", e.getMessage());
		}

	}

	@Test
	public void testModifyOrderMethodWhenOrderIdIsNull() {
		CustomerOrder order = new CustomerOrder();

		try {
			orderController.modifyOrder(order);
			fail("Expected an NullPointerException to be thrown ");
		} catch (NullPointerException e) {
			assertEquals("Order id is null", e.getMessage());
		}
	}

	@Test
	public void testModifyOrderMethodWhenOrderIdIsZero() {
		CustomerOrder order = new CustomerOrder();
		order.setOrderId(0l);

		try {
			orderController.modifyOrder(order);
			fail("Expected an IllegalArgumentException to be thrown ");
		} catch (IllegalArgumentException e) {
			assertEquals("Order id cannot be 0 while creating order", e.getMessage());
		}
	}

	@Test
	public void testModifyOrderMethodWhenOrderIdIsNegative() {
		CustomerOrder order = new CustomerOrder();
		order.setOrderId(-1l);

		try {
			orderController.modifyOrder(order);
			fail("Expected an IllegalArgumentException to be thrown ");
		} catch (IllegalArgumentException e) {
			assertEquals("Order id cannot be -1 while creating order", e.getMessage());
		}
	}

	@Test
	public void testModifyOrderMethodWhenOrderCategoryIsNull() {
		CustomerOrder order = new CustomerOrder();
		Long orderId = 1l;
		order.setOrderId(orderId);
		try {
			orderController.modifyOrder(order);
			fail("Expected an NullPointerException to be thrown ");
		} catch (NullPointerException e) {
			assertEquals("Category is null", e.getMessage());
		}
	}

	@Test
	public void testModifyOrderMethodWhenOrderDescriptionIsNull() {
		CustomerOrder order = new CustomerOrder();
		Long orderId = 1l;
		order.setOrderId(orderId);
		order.setCategory(CategoryEnum.PLUMBER);
		try {
			orderController.modifyOrder(order);
			fail("Expected an NullPointerException to be thrown ");
		} catch (NullPointerException e) {
			assertEquals("Order description is null", e.getMessage());
		}
	}

	@Test
	public void testModifyOrderMethodWhenOrderStatusIsNull() {
		CustomerOrder order = new CustomerOrder();
		Long orderId = 1l;
		order.setOrderId(orderId);
		order.setCategory(CategoryEnum.PLUMBER);
		order.setOrderDescription("testing description");

		try {
			orderController.modifyOrder(order);
			fail("Expected an NullPointerException to be thrown ");
		} catch (NullPointerException e) {
			assertEquals("Order status is null", e.getMessage());
		}
	}

	@Test
	public void testModifyOrderMethodWhenOrderWorkerIsNull() {
		CustomerOrder order = new CustomerOrder();
		Long orderId = 1l;
		order.setOrderId(orderId);
		order.setCategory(CategoryEnum.PLUMBER);
		order.setOrderDescription("testing description");
		order.setOrderStatus(OrderStatusEnum.COMPLETED);

		try {
			orderController.modifyOrder(order);
			fail("Expected an NullPointerException to be thrown ");
		} catch (NullPointerException e) {
			assertEquals("Worker is null", e.getMessage());
		}
	}

	@Test
	public void testModifyOrderMethodWhenOrderDescriptionIsEmptyString() {
		Long orderId = 1l;

		CustomerOrder order = new CustomerOrder(orderId, CategoryEnum.PLUMBER, "", OrderStatusEnum.PENDING, new Worker());
		try {
			orderController.modifyOrder(order);
			fail("Expected an IllegalArgumentException to be thrown ");
		} catch (IllegalArgumentException e) {
			assertEquals("Order description cannot be empty", e.getMessage());
		}
	}

	@Test
	public void testModifyOrderMethodWhenOrderDescriptionIsGreaterThen50Charachters() {
		// 62 length string
		final String orderDescription = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		Long orderId = 1l;

		CustomerOrder order = new CustomerOrder(orderId, CategoryEnum.PLUMBER, orderDescription, OrderStatusEnum.PENDING, new Worker());
		try {
			orderController.modifyOrder(order);
			fail("Expected an IllegalArgumentException to be thrown ");
		} catch (IllegalArgumentException e) {
			assertEquals("Order description cannot be greater than 50 characters", e.getMessage());
		}
	}

	@Test
	public void testModifyOrderMethodWhenOrderDescriptionIsExactly50Characters() {
		final String orderDescription = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWX";
		Long orderId = 1l;
		Worker worker = new Worker();
		worker.setWorkerId(1l);

		CustomerOrder order = new CustomerOrder(orderId, CategoryEnum.PLUMBER, orderDescription, OrderStatusEnum.PENDING, worker);
		try {
			orderController.modifyOrder(order);
		} catch (IllegalArgumentException e) {
			fail("No IllegalArgumentException should be thrown for exactly 50 characters description");
		}
	}

	@Test
	public void testModifyOrderMethodWhenWorkerIdIsNull() {
		final String orderDescription = "abcdefghijklmnopqrstuvwxyzasdf123";
		Long orderId = 1l;

		CustomerOrder order = new CustomerOrder(orderId, CategoryEnum.PLUMBER, orderDescription, OrderStatusEnum.PENDING, new Worker());
		try {
			orderController.modifyOrder(order);
			fail("Expected an NullPointerException to be thrown ");
		} catch (NullPointerException e) {
			assertEquals("Worker ID is null", e.getMessage());
		}
	}

	@Test
	public void testModifyOrderMethodWhenWorkerIdIsZero() {
		final String orderDescription = "abcdefghijklmnopqrstuvwxyzasdf123";
		Worker worker = new Worker();
		worker.setWorkerId(0l);
		Long orderId = 1l;
		CustomerOrder order = new CustomerOrder(orderId, CategoryEnum.PLUMBER, orderDescription, OrderStatusEnum.PENDING, worker);
		try {
			orderController.modifyOrder(order);
			fail("Expected an IllegalArgumentException to be thrown ");
		} catch (IllegalArgumentException e) {
			assertEquals("Worker ID must be a positive integer", e.getMessage());
		}
	}

	@Test
	public void testModifyOrderMethodWhenWorkerIdIsNegative() {
		final String orderDescription = "abcdefghijklmnopqrstuvwxyzasdf123";
		Worker worker = new Worker();
		Long orderId = 1l;
		worker.setWorkerId(-1l);
		CustomerOrder order = new CustomerOrder(orderId, CategoryEnum.PLUMBER, orderDescription, OrderStatusEnum.PENDING, worker);
		try {
			orderController.modifyOrder(order);
			fail("Expected an IllegalArgumentException to be thrown ");
		} catch (IllegalArgumentException e) {
			assertEquals("Worker ID must be a positive integer", e.getMessage());
		}
	}

	@Test
	public void testModifyOrderMethodWhenWorkerNotFound() {
		final String orderDescription = "abcdefghijklmnopqrstuvwxyzasdf123";
		OrderStatusEnum status = OrderStatusEnum.PENDING;
		Long workerId = 1l;
		Long orderId = 1l;

		Worker worker = new Worker();
		worker.setWorkerId(workerId);
		CustomerOrder order = new CustomerOrder(orderId, CategoryEnum.PLUMBER, orderDescription, status, worker);
		when(orderRepository.findById(orderId)).thenReturn(order);

		when(workerRepository.findById(workerId)).thenReturn(null);
		orderController.modifyOrder(order);
		InOrder inOrder = inOrder(orderRepository, orderView, workerRepository);
		inOrder.verify(orderView).showError("No Worker found with id " + workerId, order);
		verifyNoMoreInteractions(ignoreStubs(orderRepository));
	}

	@Test
	public void testModifyOrderMethodWhenOldOrderIdNotFound() {
		final String orderDescription = "abcdefghijklmnopqrstuvwxyzasdf123";
		OrderStatusEnum status = OrderStatusEnum.PENDING;
		Long orderId = 1l;
		Long workerId = 1l;

		Worker worker = new Worker();
		worker.setWorkerId(workerId);

		CustomerOrder order = new CustomerOrder(orderId, CategoryEnum.PLUMBER, orderDescription, status, worker);
		when(workerRepository.findById(workerId)).thenReturn(worker);
		when(orderRepository.findById(orderId)).thenReturn(null);
		orderController.modifyOrder(order);
		InOrder inOrder = inOrder(orderRepository, orderView, workerRepository);
		inOrder.verify(orderView).showError("No Order found with id " + orderId, null);
		verifyNoMoreInteractions(ignoreStubs(orderRepository));
	}

	// old category Plumber, new category ELECTRICIAN but
	// old worker category is plumber newWorker is old worker
	@Test
	public void testModifyOrderMethodWhenCategoryChangedButWorkerNotChanged() {
		final String orderDescription = "Plumbing Required";
		Long orderId = 1l;
		Long workerId = 1l;
		OrderStatusEnum status = OrderStatusEnum.PENDING;
		CategoryEnum oldCategory = CategoryEnum.PLUMBER;
		CategoryEnum newCategory = CategoryEnum.ELECTRICIAN;
		Worker worker = new Worker();
		worker.setWorkerId(workerId);
		worker.setCategory(oldCategory);

		// Create old order with old category
		CustomerOrder oldOrder = new CustomerOrder(orderId, oldCategory, orderDescription, status, worker);
		// Create new order with new category
		CustomerOrder newOrder = new CustomerOrder(orderId, newCategory, orderDescription, status, worker); // Worker category remains
																							// the same
		// Stub worker repository to return the worker
		when(workerRepository.findById(workerId)).thenReturn(worker);
		// Stub order repository to return the old order
		when(orderRepository.findById(orderId)).thenReturn(oldOrder);
		// Modify the worker's category to simulate a change
		worker.setCategory(newCategory);
		// Call the method under test
		orderController.modifyOrder(newOrder);

		// Verify that the error is triggered
		InOrder inOrder = inOrder(orderRepository, orderView, workerRepository);
		inOrder.verify(orderView).showError("Worker and order category must change togather", newOrder);
		verifyNoMoreInteractions(ignoreStubs(orderRepository));
	}

	// old category Plumber, new category Plumber but
	// old worker category is Plumber, newWorker category is ELECTRICIAN
	@Test
	public void testModifyOrderMethodWhenNewWorkerHasPlumbingCategoryButOrderHasElectrcianCategory() {
		final String orderDescription = "Pluming Required";
		Long orderId = 1l;
		Long workerId = 1l;
		Long newWorkerId = 2l;
		OrderStatusEnum status = OrderStatusEnum.PENDING;
		CategoryEnum oldCategory = CategoryEnum.PLUMBER;
		CategoryEnum newCategory = CategoryEnum.ELECTRICIAN;
		Worker oldWorker = new Worker();
		oldWorker.setWorkerId(workerId);
		oldWorker.setCategory(oldCategory);

		Worker newWorker = new Worker();
		newWorker.setWorkerId(newWorkerId);
		newWorker.setCategory(newCategory);

		CustomerOrder oldOrder = new CustomerOrder(orderId, oldCategory, orderDescription, status, oldWorker);
		CustomerOrder newOrder = new CustomerOrder(orderId, oldCategory, orderDescription, status, newWorker);
		when(workerRepository.findById(newWorkerId)).thenReturn(newWorker);
		when(orderRepository.findById(orderId)).thenReturn(oldOrder);
		orderController.modifyOrder(newOrder);
		InOrder inOrder = inOrder(orderRepository, orderView, workerRepository);
		inOrder.verify(orderView).showError("Worker and order category must change togather", newOrder);
		verifyNoMoreInteractions(ignoreStubs(orderRepository));
	}

	@Test
	public void testModifyOrderMethodWhenOrderNewWorkerHasPendingOrders() {
		final String orderDescription = "old order description";
		OrderStatusEnum status = OrderStatusEnum.PENDING;

		Long oldWorkerId = 1l;

		Worker oldWorker = new Worker("Alic", CategoryEnum.PLUMBER);
		oldWorker.setWorkerId(oldWorkerId);

		Long newWorkerId = 2l;

		Worker newWorker = new Worker("Bob", CategoryEnum.PLUMBER);
		newWorker.setWorkerId(newWorkerId);

		Long orderId = 1l;
		CustomerOrder previousPendingOrder = new CustomerOrder(CategoryEnum.PLUMBER, orderDescription, OrderStatusEnum.PENDING,
				newWorker);
		previousPendingOrder.setOrderId(orderId);
		newWorker.setOrders(asList(previousPendingOrder));

		CustomerOrder oldOrder = new CustomerOrder(orderId, CategoryEnum.PLUMBER, orderDescription, status, oldWorker);
		CustomerOrder updatedOrder = new CustomerOrder(orderId, CategoryEnum.PLUMBER, orderDescription, status, newWorker);

		when(orderRepository.findById(orderId)).thenReturn(oldOrder);
		when(workerRepository.findById(newWorkerId)).thenReturn(newWorker);
		orderController.modifyOrder(updatedOrder);

		InOrder inOrder = inOrder(orderRepository, orderView, workerRepository);
		inOrder.verify(orderView).showError("Cannot assign orders to this worker because it is pending orders",
				updatedOrder);
		verifyNoMoreInteractions(ignoreStubs(orderRepository));
	}

	@Test
	public void testModifyOrderMethodWhenOrderNewWorkerHasNullPendingOrders() {
		final String orderDescription = "old order description";
		OrderStatusEnum status = OrderStatusEnum.PENDING;

		Long oldWorkerId = 1l;

		Worker oldWorker = new Worker("Alic", CategoryEnum.PLUMBER);
		oldWorker.setWorkerId(oldWorkerId);

		Long newWorkerId = 2l;

		Worker newWorker = new Worker("Bob", CategoryEnum.PLUMBER);
		newWorker.setWorkerId(newWorkerId);

		Long orderId = 1l;

		CustomerOrder oldOrder = new CustomerOrder(orderId, CategoryEnum.PLUMBER, orderDescription, status, oldWorker);
		CustomerOrder updatedOrder = new CustomerOrder(orderId, CategoryEnum.PLUMBER, orderDescription, status, newWorker);

		when(orderRepository.findById(orderId)).thenReturn(oldOrder);
		when(workerRepository.findById(newWorkerId)).thenReturn(newWorker);
		orderController.modifyOrder(updatedOrder);
		InOrder inOrder = inOrder(orderRepository, orderView, workerRepository);
		inOrder.verify(orderRepository).save(updatedOrder);
		inOrder.verify(orderView).orderModified(updatedOrder);
		verifyNoMoreInteractions(ignoreStubs(orderRepository));
	}

	@Test
	public void testModifyOrderMethodWhenOrderNewWorkerHasNoPendingOrders() {
		final String orderDescription = "old order description";
		OrderStatusEnum status = OrderStatusEnum.PENDING;

		Long oldWorkerId = 1l;

		Worker oldWorker = new Worker("Alic", CategoryEnum.PLUMBER);
		oldWorker.setWorkerId(oldWorkerId);

		Long newWorkerId = 2l;

		Worker newWorker = new Worker("Bob", CategoryEnum.PLUMBER);
		newWorker.setWorkerId(newWorkerId);

		Long orderId = 1l;
		CustomerOrder previousPendingOrder = new CustomerOrder(CategoryEnum.PLUMBER, orderDescription, OrderStatusEnum.CANCELED,
				newWorker);
		previousPendingOrder.setOrderId(orderId);
		newWorker.setOrders(asList(previousPendingOrder));
		CustomerOrder oldOrder = new CustomerOrder(orderId, CategoryEnum.PLUMBER, orderDescription, status, oldWorker);
		CustomerOrder updatedOrder = new CustomerOrder(orderId, CategoryEnum.PLUMBER, orderDescription, status, newWorker);

		when(orderRepository.findById(orderId)).thenReturn(oldOrder);
		when(workerRepository.findById(newWorkerId)).thenReturn(newWorker);
		orderController.modifyOrder(updatedOrder);
		InOrder inOrder = inOrder(orderRepository, orderView, workerRepository);
		inOrder.verify(orderRepository).save(updatedOrder);
		inOrder.verify(orderView).orderModified(updatedOrder);
		verifyNoMoreInteractions(ignoreStubs(orderRepository));
	}

	@Test
	public void testModifyOrderMethodWhenOrderDescriptionChanged() {
		final String oldOrderDescription = "old order description";
		final String newOrderDescription = "New order description";

		Long workerId = 1l;
		Long orderId = 1l;

		Worker worker = new Worker();
		worker.setWorkerId(workerId);
		worker.setCategory(CategoryEnum.PLUMBER);

		CustomerOrder oldOrder = new CustomerOrder(orderId, CategoryEnum.PLUMBER, oldOrderDescription, OrderStatusEnum.PENDING, worker);
		CustomerOrder updatedOrder = new CustomerOrder(orderId, CategoryEnum.PLUMBER, newOrderDescription, OrderStatusEnum.PENDING,
				worker);

		when(orderRepository.findById(orderId)).thenReturn(oldOrder);
		when(workerRepository.findById(workerId)).thenReturn(worker);
		orderController.modifyOrder(updatedOrder);

		InOrder inOrder = inOrder(orderRepository, orderView, workerRepository);
		inOrder.verify(orderRepository).save(updatedOrder);
		inOrder.verify(orderView).orderModified(updatedOrder);
		verifyNoMoreInteractions(ignoreStubs(orderRepository));
	}

	@Test
	public void testModifyOrderMethodWhenOrderStatusChanged() {
		final String orderDescription = "old order description";
		OrderStatusEnum oldStatus = OrderStatusEnum.PENDING;
		OrderStatusEnum newStatus = OrderStatusEnum.COMPLETED;

		Long workerId = 1l;
		Long orderId = 1l;

		Worker worker = new Worker();
		worker.setWorkerId(workerId);
		worker.setCategory(CategoryEnum.PLUMBER);
		CustomerOrder oldOrder = new CustomerOrder(orderId, CategoryEnum.PLUMBER, orderDescription, oldStatus, worker);
		CustomerOrder updatedOrder = new CustomerOrder(orderId, CategoryEnum.PLUMBER, orderDescription, newStatus, worker);

		when(orderRepository.findById(orderId)).thenReturn(oldOrder);
		when(workerRepository.findById(workerId)).thenReturn(worker);
		orderController.modifyOrder(updatedOrder);

		InOrder inOrder = inOrder(orderRepository, orderView, workerRepository);
		inOrder.verify(orderRepository).save(updatedOrder);
		inOrder.verify(orderView).orderModified(updatedOrder);
		verifyNoMoreInteractions(ignoreStubs(orderRepository));
	}

	@Test
	public void testDeleteOrderMethodWhenIdIsNull() {
		Long orderId = null;
		try {
			orderController.deleteOrders(orderId);
			fail("Expected an NullPointerException to be thrown ");
		} catch (NullPointerException e) {
			assertEquals("Order id is null", e.getMessage());
		}
	}

	@Test
	public void testDeleteOrderMethodWhenIdIsZero() {
		Long orderId = 0l;
		try {
			orderController.deleteOrders(orderId);
			fail("Expected an IllegalArgumentException to be thrown ");
		} catch (IllegalArgumentException e) {
			assertEquals("Order id cannot be 0 while deleting order", e.getMessage());
		}
	}

	@Test
	public void testDeleteOrderMethodWhenIdIsNegative() {
		Long orderId = -1l;
		try {
			orderController.deleteOrders(orderId);
			fail("Expected an IllegalArgumentException to be thrown ");
		} catch (IllegalArgumentException e) {
			assertEquals("Order id cannot be -1 while deleting order", e.getMessage());
		}
	}

	@Test
	public void testDeleteOrderMethodWhenOrderNotFound() {
		Long orderId = 1l;
		when(orderRepository.findById(orderId)).thenReturn(null);
		orderController.deleteOrders(orderId);
		InOrder inOrder = inOrder(orderRepository, orderView);
		inOrder.verify(orderView).showErrorOrderNotFound("No Order found with id " + orderId, null);
		verifyNoMoreInteractions(ignoreStubs(orderRepository));
	}

	@Test
	public void testDeleteOrderMethodWhenOrderFound() {
		Long orderId = 1l;
		CustomerOrder order = new CustomerOrder();
		order.setOrderId(orderId);
		when(orderRepository.findById(orderId)).thenReturn(order);
		orderController.deleteOrders(orderId);
		InOrder inOrder = inOrder(orderRepository, orderView);
		inOrder.verify(orderRepository).delete(order);

		inOrder.verify(orderView).orderRemoved(order);

		verifyNoMoreInteractions(ignoreStubs(orderRepository));
	}

}
