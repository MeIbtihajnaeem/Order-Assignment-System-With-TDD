package com.example.orderAssignmentSystem.controller;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.ignoreStubs;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
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
import org.mockito.Spy;

//import com.example.orderAssignmentSystem.model.Category;
import com.example.orderAssignmentSystem.model.Order;
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

	@Test
	public void testAllOrders() {
		List<Order> orders = asList(new Order());
		when(orderRepository.findAll()).thenReturn(orders);
		orderController.allCategories();
		verify(orderView).showAllOrders(orders);
	}

	@Test
	public void testAllOrdersWhenEmptyList() {
		when(orderRepository.findAll()).thenReturn(Collections.emptyList());
		orderController.allCategories();
		verify(orderView).showAllOrders(Collections.emptyList());
	}

	@Test
	public void testAllOrdersWhenNullList() {
		when(orderRepository.findAll()).thenReturn(null);
		orderController.allCategories();
		verify(orderView).showAllOrders(null);
	}

	@Test
	public void testCreateNewOrderWhenNullOrder() {
		try {
			orderController.createNewOrder(null);
			fail("Expected an NullPointerException to be thrown ");
		} catch (NullPointerException e) {
			assertEquals("Order cannot be null", e.getMessage());
		}
		verifyNoMoreInteractions(orderRepository);
		verifyNoMoreInteractions(orderView);
	}

	private static final String DEFAULT_ORDER_ID = "1";
	private static final String DEFAULT_WORKER_ID = "1";
	private static final CategoryEnum DEFAULT_CATEGORY = CategoryEnum.PLUMBER;
	private static final String DEFAULT_ORDER_DESCRIPTION = "New Pipe installation";

	@Spy
	private Order newOrderObject = new Order(DEFAULT_ORDER_ID, DEFAULT_CATEGORY, DEFAULT_ORDER_DESCRIPTION,
			DEFAULT_WORKER_ID);

	@Test
	public void testCreateNewOrderWhenOrderIdNotAlreadyExistsButWorkerExists() {

		when(orderRepository.findById(DEFAULT_ORDER_ID)).thenReturn(null);

		when(workerRepository.findById(DEFAULT_WORKER_ID)).thenReturn(new Worker(DEFAULT_WORKER_ID, "Alic"));

		orderController.createNewOrder(newOrderObject);
		InOrder inOrder = inOrder(orderRepository, orderView, workerRepository, newOrderObject);
		inOrder.verify(workerRepository).findById(DEFAULT_WORKER_ID);
		inOrder.verify(newOrderObject).setOrderStatus(OrderStatusEnum.PENDING);
		inOrder.verify(orderRepository).save(newOrderObject);
		inOrder.verify(orderView).orderAdded(newOrderObject);
		verifyNoMoreInteractions(ignoreStubs(orderRepository));
	}

	@Test
	public void testCreateNewOrderWhenOrderIdNotAlreadyExistsButNoWorkerExists() {
		Order order = new Order(DEFAULT_ORDER_ID, DEFAULT_CATEGORY, DEFAULT_ORDER_DESCRIPTION, DEFAULT_WORKER_ID);
		when(orderRepository.findById(DEFAULT_ORDER_ID)).thenReturn(null);
		when(workerRepository.findById(DEFAULT_WORKER_ID)).thenReturn(null);
		orderController.createNewOrder(order);
		InOrder inOrder = inOrder(orderRepository, orderView, workerRepository);
		inOrder.verify(orderView).showError("Worker not found", null);
		verifyNoMoreInteractions(ignoreStubs(orderRepository));
	}

	@Test
	public void testCreateNewOrderWhenOrderIdNotAlreadyExistsButIsNull() {
		Order order = new Order(DEFAULT_ORDER_ID, DEFAULT_CATEGORY, DEFAULT_ORDER_DESCRIPTION, null);
		when(orderRepository.findById(DEFAULT_ORDER_ID)).thenReturn(null);
		orderController.createNewOrder(order);
		InOrder inOrder = inOrder(orderRepository, orderView, workerRepository);
		inOrder.verify(orderView).showError("Cannot create Order without Worker", null);
		verifyNoMoreInteractions(ignoreStubs(orderRepository));
	}

	@Test
	public void testCreateNewOrderWhenOrderIdAlreadyExists() {
		Order order = new Order(DEFAULT_ORDER_ID, DEFAULT_CATEGORY, DEFAULT_ORDER_DESCRIPTION);
		Order existingOrder = new Order(DEFAULT_ORDER_ID, CategoryEnum.ELECTRICIAN, "New wire installation");
		when(orderRepository.findById(DEFAULT_ORDER_ID)).thenReturn(existingOrder);
		orderController.createNewOrder(order);
		InOrder inOrder = inOrder(orderRepository, orderView);
		inOrder.verify(orderView).showError("Order with id " + order.getOrderId() + " already exists", existingOrder);
		verifyNoMoreInteractions(ignoreStubs(orderRepository));
	}

	@Test
	public void testDeleteOrderWhenNullOrder() {
		try {
			orderController.deleteOrder(null);
			fail("Expected an NullPointerException to be thrown ");
		} catch (NullPointerException e) {
			assertEquals("Order cannot be null", e.getMessage());
		}
		verifyNoMoreInteractions(orderRepository);
		verifyNoMoreInteractions(orderView);
	}

	@Test
	public void testDeleteOrderWhenOrderNotAlreadyExists() {
		Order order = new Order(DEFAULT_ORDER_ID, DEFAULT_CATEGORY, DEFAULT_ORDER_DESCRIPTION);
		when(orderRepository.findById(DEFAULT_ORDER_ID)).thenReturn(null);
		orderController.deleteOrder(order);
		InOrder inOrder = inOrder(orderRepository, orderView);
		inOrder.verify(orderView).showErrorOrderNotFound("No order exist with id " + order.getOrderId(), null);
		verifyNoMoreInteractions(ignoreStubs(orderRepository));
	}

	@Test
	public void testDeleteOrderWhenOrderAlreadyExists() {
		Order order = new Order(DEFAULT_ORDER_ID, DEFAULT_CATEGORY, DEFAULT_ORDER_DESCRIPTION);
		when(orderRepository.findById(DEFAULT_ORDER_ID)).thenReturn(order);
		orderController.deleteOrder(order);
		InOrder inOrder = inOrder(orderRepository, orderView);
		inOrder.verify(orderRepository).delete(order.getOrderId());
		inOrder.verify(orderView).orderRemoved(order);
		verifyNoMoreInteractions(ignoreStubs(orderRepository));
	}

	@Test
	public void testModifyOrderStatusWhenNull() {
		Order order = new Order(DEFAULT_ORDER_ID, DEFAULT_CATEGORY, DEFAULT_ORDER_DESCRIPTION, DEFAULT_ORDER_ID,
				OrderStatusEnum.PENDING);
		try {
			orderController.modifyOrderStatus(order, null);
			fail("Expected an NullPointerException to be thrown ");
		} catch (NullPointerException e) {
			assertEquals("Status cannot be null", e.getMessage());
		}
		verifyNoMoreInteractions(orderRepository);
		verifyNoMoreInteractions(orderView);
	}

	@Test
	public void testModifyOrderWhenStatusAndOrderNull() {
		try {
			orderController.modifyOrderStatus(null, null);
			fail("Expected an NullPointerException to be thrown ");
		} catch (NullPointerException e) {
			assertEquals("Order cannot be null", e.getMessage());
		}
		verifyNoMoreInteractions(orderRepository);
		verifyNoMoreInteractions(orderView);
	}

	@Spy
	private Order orderForStatusChange = new Order(DEFAULT_ORDER_ID, DEFAULT_CATEGORY, DEFAULT_ORDER_DESCRIPTION,
			DEFAULT_ORDER_ID, OrderStatusEnum.PENDING);

	@Test
	public void testModifyOrderStatusWhenChanged() {
		when(orderRepository.findById(DEFAULT_ORDER_ID)).thenReturn(orderForStatusChange);
		orderController.modifyOrderStatus(orderForStatusChange, OrderStatusEnum.COMPLETED);
		InOrder inOrder = inOrder(orderRepository, orderView, orderForStatusChange);
		inOrder.verify(orderForStatusChange).setOrderStatus(OrderStatusEnum.COMPLETED);
		inOrder.verify(orderRepository).update(orderForStatusChange);
		inOrder.verify(orderView).orderModified(orderForStatusChange);
		verifyNoMoreInteractions(ignoreStubs(orderRepository));
	}

	@Test
	public void testModifyOrderStatusWhenAlreadyCompleted() {
		Order order = new Order(DEFAULT_ORDER_ID, DEFAULT_CATEGORY, DEFAULT_ORDER_DESCRIPTION, DEFAULT_ORDER_ID,
				OrderStatusEnum.COMPLETED);
		when(orderRepository.findById(DEFAULT_ORDER_ID)).thenReturn(order);
		orderController.modifyOrderStatus(order, OrderStatusEnum.COMPLETED);
		verify(orderRepository).findById(DEFAULT_ORDER_ID);
		verifyNoInteractions(orderView);
		verifyNoMoreInteractions(ignoreStubs(orderRepository));

	}

	@Test
	public void testModifyOrderStatusWhenOrderIdNotFound() {
		Order order = new Order(DEFAULT_ORDER_ID, DEFAULT_CATEGORY, DEFAULT_ORDER_DESCRIPTION, DEFAULT_ORDER_ID,
				OrderStatusEnum.COMPLETED);
		when(orderRepository.findById(DEFAULT_ORDER_ID)).thenReturn(null);
		orderController.modifyOrderStatus(order, OrderStatusEnum.COMPLETED);
		verify(orderRepository).findById(DEFAULT_ORDER_ID);
		verify(orderView).showErrorOrderNotFound("No order exist with id 1", null);
		verifyNoMoreInteractions(ignoreStubs(orderRepository));
	}

}
