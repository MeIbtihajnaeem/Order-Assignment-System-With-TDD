package com.example.orderAssignmentSystem.raceCondition;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.orderAssignmentSystem.controller.OrderController;
import com.example.orderAssignmentSystem.model.CustomerOrder;
import com.example.orderAssignmentSystem.model.Worker;
import com.example.orderAssignmentSystem.model.enums.CategoryEnum;
import com.example.orderAssignmentSystem.model.enums.OrderStatusEnum;
import com.example.orderAssignmentSystem.repository.OrderRepository;
import com.example.orderAssignmentSystem.repository.WorkerRepository;
import com.example.orderAssignmentSystem.view.OrderView;

public class OrderControllerRaceConditionTest {

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
	public void setUp() throws Exception {
		closeable = MockitoAnnotations.openMocks(this);
//		entityManagerFactory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
//
//		entityManager = entityManagerFactory.createEntityManager();
//		orderRepository = new OrderDatabaseRepository(entityManager);
//		workerRepository = new WorkerDatabaseRepository(entityManager);
//		orderController = new OrderController(orderRepository, orderView, workerRepository);
	}

	@After
	public void releaseMocks() throws Exception {
		closeable.close();
	}

	@Test
	public void testNewCustomerOrderConcurrent() {
		// Given
		Worker worker = new Worker("Jhon", CategoryEnum.PLUMBER); // Assuming a worker with ID 1 and category "Category"
		worker.setWorkerId(1l);
		CustomerOrder order1 = new CustomerOrder(CategoryEnum.PLUMBER, "Description 1", OrderStatusEnum.PENDING,
				worker);
//		CustomerOrder order2 = new CustomerOrder(CategoryEnum.PLUMBER, "Description 2", OrderStatusEnum.PENDING,
//				worker);

		List<CustomerOrder> orders = new ArrayList<CustomerOrder>();

		when(workerRepository.findById(anyLong())).thenReturn(worker);
//		when(orderRepository.save(any(CustomerOrder.class))).thenAnswer(invocation -> {
//			CustomerOrder savedOrder = invocation.getArgument(0);
//			savedOrder.setOrderId(1l); // Simulate saving with a generated order ID
//			return savedOrder;
//		});
		when(orderRepository.save(any(CustomerOrder.class)))
				.thenAnswer(invocation -> orders.stream().findFirst().orElse(null));

		doAnswer(invocation -> {
			orders.add(order1);
			worker.setOrders(asList(order1));
//			orders.add(order2);
			return null;
		}).when(orderRepository).save(any(CustomerOrder.class));

		List<Thread> threads = IntStream.range(0, 10)
				.mapToObj(i -> new Thread(() -> orderController.createNewOrder(order1))).peek(t -> t.start())
				.collect(Collectors.toList());

		await().atMost(10, TimeUnit.SECONDS).until(() -> threads.stream().noneMatch(t -> t.isAlive()));
		assertThat(orders).containsExactly(order1);
	}
}
