package com.example.orderAssignmentSystem.controller;

import static java.util.Arrays.asList;
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

import com.example.orderAssignmentSystem.model.Category;
import com.example.orderAssignmentSystem.model.Order;
import com.example.orderAssignmentSystem.model.Worker;
import com.example.orderAssignmentSystem.model.enums.OrderStatus;
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
		orderController.createNewOrder(null);
		verifyNoMoreInteractions(orderRepository);
		verify(orderView).showError("Order cannot be null", null);

	}

	@Spy
	private Order newOrderObject = new Order("1", new Category("1", "Plumber"), "New Pipe installation", "1");

	@Test
	public void testCreateNewOrderWhenOrderIdNotAlreadyExistsButWorkerExists() {

		when(orderRepository.findById("1")).thenReturn(null);

		when(workerRepository.findById("1")).thenReturn(new Worker("1", "Alic"));

		orderController.createNewOrder(newOrderObject);
		InOrder inOrder = inOrder(orderRepository, orderView, workerRepository, newOrderObject);
		inOrder.verify(workerRepository).findById("1");
		inOrder.verify(newOrderObject).setOrderStatus(OrderStatus.PENDING);
		inOrder.verify(orderRepository).save(newOrderObject);
		inOrder.verify(orderView).orderAdded(newOrderObject);
		verifyNoMoreInteractions(ignoreStubs(orderRepository));
	}

	@Test
	public void testCreateNewOrderWhenOrderIdNotAlreadyExistsButNoWorkerExists() {
		Order order = new Order("1", new Category("1", "Plumber"), "New Pipe installation", "1");
		when(orderRepository.findById("1")).thenReturn(null);
		when(workerRepository.findById("1")).thenReturn(null);
		orderController.createNewOrder(order);
		InOrder inOrder = inOrder(orderRepository, orderView, workerRepository);
		inOrder.verify(orderView).showError("Worker not found", null);
		verifyNoMoreInteractions(ignoreStubs(orderRepository));
	}

	@Test
	public void testCreateNewOrderWhenOrderIdNotAlreadyExistsButIsNull() {
		Order order = new Order("1", new Category("1", "Plumber"), "New Pipe installation", null);
		when(orderRepository.findById("1")).thenReturn(null);
		orderController.createNewOrder(order);
		InOrder inOrder = inOrder(orderRepository, orderView, workerRepository);
		inOrder.verify(orderView).showError("Cannot create Order without Worker", null);
		verifyNoMoreInteractions(ignoreStubs(orderRepository));
	}

	@Test
	public void testCreateNewOrderWhenOrderIdAlreadyExists() {
		Order order = new Order("1", new Category("1", "Plumber"), "New Pipe installation");
		Order existingOrder = new Order("1", new Category("1", "Electrician"), "New wire installation");
		when(orderRepository.findById("1")).thenReturn(existingOrder);
		orderController.createNewOrder(order);
		InOrder inOrder = inOrder(orderRepository, orderView);
		inOrder.verify(orderView).showError("Order with id " + order.getOrderId() + " already exists", existingOrder);
		verifyNoMoreInteractions(ignoreStubs(orderRepository));
	}

	@Test
	public void testDeleteOrderWhenNullOrder() {
		orderController.deleteOrder(null);
		verifyNoMoreInteractions(orderRepository);
		verify(orderView).showError("Order cannot be null ", null);

	}

	@Test
	public void testDeleteOrderWhenOrderNotAlreadyExists() {
		Order order = new Order("1", new Category("1", "Plumber"), "New Pipe installation");
		when(orderRepository.findById("1")).thenReturn(null);
		orderController.deleteOrder(order);
		InOrder inOrder = inOrder(orderRepository, orderView);
		inOrder.verify(orderView).showErrorOrderNotFound("No order exist with id " + order.getOrderId(), null);
		verifyNoMoreInteractions(ignoreStubs(orderRepository));
	}

	@Test
	public void testDeleteOrderWhenOrderAlreadyExists() {
		Order order = new Order("1", new Category("1", "Plumber"), "New Pipe installation");
		when(orderRepository.findById("1")).thenReturn(order);
		orderController.deleteOrder(order);
		InOrder inOrder = inOrder(orderRepository, orderView);
		inOrder.verify(orderRepository).delete(order.getOrderId());
		inOrder.verify(orderView).orderRemoved(order);
		verifyNoMoreInteractions(ignoreStubs(orderRepository));
	}

	@Test
	public void testModifyOrderStatusWhenNull() {
		Order order = new Order("1", new Category("1", "Plumber"), "New Pipe installation", "1", OrderStatus.PENDING);
		orderController.modifyOrderStatus(order, null);
		verifyNoMoreInteractions(orderRepository);
		verify(orderView).showError("Status cannot be null ", null);
	}

	@Test
	public void testModifyOrderWhenStatusAndOrderNull() {
		orderController.modifyOrderStatus(null, null);
		verifyNoMoreInteractions(orderRepository);
		verify(orderView).showError("Order cannot be null ", null);
	}

	@Spy
	private Order orderForStatusChange = new Order("1", new Category("1", "Plumber"), "New Pipe installation", "1",
			OrderStatus.PENDING);

	@Test
	public void testModifyOrderStatusWhenChanged() {
		when(orderRepository.findById("1")).thenReturn(orderForStatusChange);
		orderController.modifyOrderStatus(orderForStatusChange, OrderStatus.COMPLETED);
		InOrder inOrder = inOrder(orderRepository, orderView, orderForStatusChange);
		inOrder.verify(orderForStatusChange).setOrderStatus(OrderStatus.COMPLETED);
		inOrder.verify(orderRepository).update(orderForStatusChange);
		inOrder.verify(orderView).orderModified(orderForStatusChange);
		verifyNoMoreInteractions(ignoreStubs(orderRepository));
	}

	@Test
	public void testModifyOrderStatusWhenAlreadyCompleted() {
		Order order = new Order("1", new Category("1", "Plumber"), "New Pipe installation", "1", OrderStatus.COMPLETED);
		when(orderRepository.findById("1")).thenReturn(order);
		orderController.modifyOrderStatus(order, OrderStatus.COMPLETED);
		verify(orderRepository).findById("1");
		verifyNoInteractions(orderView);
		verifyNoMoreInteractions(ignoreStubs(orderRepository));

	}

	@Test
	public void testModifyOrderStatusWhenOrderIdNotFound() {
		Order order = new Order("1", new Category("1", "Plumber"), "New Pipe installation", "1", OrderStatus.COMPLETED);
		when(orderRepository.findById("1")).thenReturn(null);
		orderController.modifyOrderStatus(order, OrderStatus.COMPLETED);
		verify(orderRepository).findById("1");
		verify(orderView).showErrorOrderNotFound("No order exist with id 1", null);
		verifyNoMoreInteractions(ignoreStubs(orderRepository));
	}

}
