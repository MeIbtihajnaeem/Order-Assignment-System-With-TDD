package com.example.orderAssignmentSystem.controller;

import static java.util.Arrays.asList;
import static org.mockito.Mockito.verify;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.orderAssignmentSystem.model.CustomerOrder;
import com.example.orderAssignmentSystem.model.Worker;
import com.example.orderAssignmentSystem.model.enums.CategoryEnum;
import com.example.orderAssignmentSystem.model.enums.OrderStatusEnum;
import com.example.orderAssignmentSystem.repository.OrderRepository;
import com.example.orderAssignmentSystem.repository.WorkerRepository;
import com.example.orderAssignmentSystem.repository.postgresql.OrderDatabaseRepository;
import com.example.orderAssignmentSystem.repository.postgresql.WorkerDatabaseRepository;
import com.example.orderAssignmentSystem.view.OrderView;

public class OrderControllerTestIT {

	@Mock
	private OrderRepository orderRepository;

	@Mock
	private OrderView orderView;

	@Mock
	private WorkerRepository workerRepository;

	@InjectMocks
	private OrderController orderController;

	private AutoCloseable closeable;

	private EntityManagerFactory entityManagerFactory;
	private EntityManager entityManager;
	private static final String PERSISTENCE_UNIT_NAME = "myPersistenceUnit";
	private static final String DEFAULT_WORKER_CodiceFiscale = "9j4RWZ5b0hnqA8eG";

	@Before
	public void setUp() {
		closeable = MockitoAnnotations.openMocks(this);
		entityManagerFactory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);

		entityManager = entityManagerFactory.createEntityManager();
		orderRepository = new OrderDatabaseRepository(entityManager);
		workerRepository = new WorkerDatabaseRepository(entityManager);
		orderController = new OrderController(orderRepository, orderView, workerRepository);
	}

	@After
	public void releaseMocks() throws Exception {
		closeable.close();
	}

	@Test
	public void testAllOrders() {
		Worker worker = new Worker("bob", DEFAULT_WORKER_CodiceFiscale);
		Worker newWorker = workerRepository.save(worker);
		CustomerOrder order = new CustomerOrder(CategoryEnum.PLUMBER, "Change pipe", OrderStatusEnum.PENDING,
				newWorker);
		CustomerOrder savedOrder = orderRepository.save(order);
		orderController.allOrders();
		verify(orderView).showAllOrders(asList(savedOrder));
	}

	@Test
	public void testNewOrder() {
		Worker worker = new Worker("alic", DEFAULT_WORKER_CodiceFiscale);
		Worker newWorker = workerRepository.save(worker);
		CustomerOrder order = new CustomerOrder(CategoryEnum.PLUMBER, "Change pipe", OrderStatusEnum.PENDING,
				newWorker);
		orderController.createNewOrder(order);
		verify(orderView).orderAdded(order);
	}

	@Test
	public void testUpdateOrder() {
		final String orderDescription = "old order description";
		OrderStatusEnum oldStatus = OrderStatusEnum.PENDING;
		OrderStatusEnum newStatus = OrderStatusEnum.COMPLETED;

		Worker worker = new Worker("alic", DEFAULT_WORKER_CodiceFiscale);
		Worker savedWorker = workerRepository.save(worker);

		CustomerOrder oldOrder = new CustomerOrder(CategoryEnum.PLUMBER, orderDescription, oldStatus, savedWorker);
		Long updatedOrderId = orderRepository.save(oldOrder).getOrderId();

		CustomerOrder updatedOrder = new CustomerOrder(CategoryEnum.ELECTRICIAN, orderDescription, newStatus,
				savedWorker);

		updatedOrder.setOrderId(updatedOrderId);

		orderController.modifyOrder(updatedOrder);
		verify(orderView).orderModified(updatedOrder);
	}

	@Test
	public void testDeleteOrder() {
		Worker worker = new Worker("Jhon", DEFAULT_WORKER_CodiceFiscale);
		Worker newWorker = workerRepository.save(worker);
		CustomerOrder order = new CustomerOrder(CategoryEnum.PLUMBER, "Change pipe", OrderStatusEnum.PENDING,
				newWorker);
		CustomerOrder savedOrder = orderRepository.save(order);
		orderController.deleteOrders(savedOrder.getOrderId());
		verify(orderView).orderRemoved(savedOrder);

	}
}
