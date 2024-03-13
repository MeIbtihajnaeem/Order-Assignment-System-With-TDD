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
import com.example.orderAssignmentSystem.view.WorkerView;

public class WorkerControllerIT {

	@Mock
	private WorkerRepository workerRepository;

	@Mock
	private WorkerView workerView;

	@InjectMocks
	private WorkerController workerController;

	private AutoCloseable closeable;

	private OrderRepository orderRepository;

	private EntityManagerFactory entityManagerFactory;
	private EntityManager entityManager;
	private static final String PERSISTENCE_UNIT_NAME = "myPersistenceUnit";
	private static final String DEFAULT_WORKER_CodiceFiscale = "9j4RWZ5b0hnqA8eG";

	@Before
	public void setup() {
		closeable = MockitoAnnotations.openMocks(this);
		entityManagerFactory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);

		entityManager = entityManagerFactory.createEntityManager();
		orderRepository = new OrderDatabaseRepository(entityManager);

		workerRepository = new WorkerDatabaseRepository(entityManager);

		workerController = new WorkerController(workerRepository, workerView);
	}

	@After
	public void releaseMocks() throws Exception {
		closeable.close();
	}

	@Test
	public void testAllWorkers() {
		Worker worker = new Worker("bob", DEFAULT_WORKER_CodiceFiscale);
		Worker newWorker = workerRepository.save(worker);
		CustomerOrder newOrder = orderRepository
				.save(new CustomerOrder(CategoryEnum.PLUMBER, "Change pipe", OrderStatusEnum.PENDING, newWorker));
		newWorker.setOrders(asList(newOrder));
		workerController.getAllWorkers();
		verify(workerView).showAllWorkers(asList(newWorker));
	}

	@Test
	public void testNewWorker() {
		Worker worker = new Worker("bob", DEFAULT_WORKER_CodiceFiscale);
		workerController.createNewWorker(worker);

		verify(workerView).workerAdded(worker);
	}

	@Test
	public void testDeleteWorker() {
		Worker worker = new Worker("Jhon", DEFAULT_WORKER_CodiceFiscale);
		Worker newWorker = workerRepository.save(worker);
		workerController.deleteWorker(newWorker.getWorkerId());
		verify(workerView).workerRemoved(newWorker);

	}
}