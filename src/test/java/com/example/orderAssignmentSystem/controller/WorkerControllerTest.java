package com.example.orderAssignmentSystem.controller;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.argThat;
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
import com.example.orderAssignmentSystem.repository.WorkerRepository;
import com.example.orderAssignmentSystem.view.WorkerView;

public class WorkerControllerTest {

	@Mock
	private WorkerRepository workerRepository;

	@Mock
	private WorkerView workerView;

	@InjectMocks
	private WorkerController workerController;

	private AutoCloseable closeable;

	@Before
	public void setup() {
		closeable = MockitoAnnotations.openMocks(this);
	}

	@After
	public void releaseMocks() throws Exception {
		closeable.close();
	}

	private static final String DEFAULT_WORKER_NAME = "Alic";

	private static final Long DEFAULT_WORKER_ID = (long) 1;

	private static final int INITIAL_INDEX = 0;

	@Test
	public void testAllworkerWhenOrderListNotEmpty() {
		List<CustomerOrder> allOrdersList = asList(new CustomerOrder());
		List<Worker> allWorkersLists = asList(new Worker(DEFAULT_WORKER_ID, DEFAULT_WORKER_NAME, allOrdersList));
		when(workerRepository.findAll()).thenReturn(allWorkersLists);
		workerController.allWorkers();
		verify(workerView).showAllWorkers(argThat(workerList -> {
			if (allWorkersLists.get(INITIAL_INDEX).getOrders().isEmpty()) {
				return false;
			}
			return true;
		}));
	}

	@Test
	public void testAllWorkersWhenOrderListEmpty() {
		List<Worker> allWorkersLists = asList(
				new Worker(DEFAULT_WORKER_ID, DEFAULT_WORKER_NAME, Collections.emptyList()));
		when(workerRepository.findAll()).thenReturn(allWorkersLists);
		workerController.allWorkers();
		verify(workerView).showAllWorkers(argThat(workerList -> {
			if (allWorkersLists.get(INITIAL_INDEX).getOrders().isEmpty()) {
				return true;
			}
			return false;
		}));
	}

	@Test
	public void testAllWorkersWhenOrderNull() {
		List<Worker> allWorkersLists = asList(new Worker(DEFAULT_WORKER_ID, DEFAULT_WORKER_NAME, null));
		when(workerRepository.findAll()).thenReturn(allWorkersLists);
		workerController.allWorkers();
		verify(workerView).showAllWorkers(argThat(workerList -> {
			if (allWorkersLists.get(INITIAL_INDEX).getOrders() == null) {
				return true;
			}
			return false;
		}));
	}

	@Test
	public void testAllOrdersWhenEmptyList() {
		when(workerRepository.findAll()).thenReturn(Collections.emptyList());
		workerController.allWorkers();
		verify(workerView).showAllWorkers(Collections.emptyList());
	}

	@Test
	public void testAllOrdersWhenNullList() {
		when(workerRepository.findAll()).thenReturn(null);
		workerController.allWorkers();
		verify(workerView).showAllWorkers(null);
	}

	@Test
	public void testCreateNewWorkerWhenWorkerIsNull() {
		try {
			workerController.createNewWorker(null);
			fail("Expected an NullPointerException to be thrown ");
		} catch (NullPointerException e) {
			assertEquals("Worker cannot be null", e.getMessage());
		}
		verifyNoMoreInteractions(workerRepository);
		verifyNoMoreInteractions(workerView);
	}

	private static final Worker WORKER_WITH_No_ORDERS = new Worker(DEFAULT_WORKER_ID, DEFAULT_WORKER_NAME);

	@Test
	public void testCreateNewWorkerWhenWorkerIdNotAlreadyExists() {
		Worker newWorker = WORKER_WITH_No_ORDERS;
		when(workerRepository.findById(DEFAULT_WORKER_ID)).thenReturn(null);
		workerController.createNewWorker(newWorker);
		InOrder inOrder = inOrder(workerRepository, workerView);
		inOrder.verify(workerRepository).save(newWorker);
		inOrder.verify(workerView).workerAdded(newWorker);
		verifyNoMoreInteractions(ignoreStubs(workerRepository));
	}

	@Test
	public void testCreateNewWorkerWhenWorkerIdAlreadyExists() {
		Worker newWorker = WORKER_WITH_No_ORDERS;
		Worker existingWorker = new Worker(DEFAULT_WORKER_ID, "Bob");
		when(workerRepository.findById(DEFAULT_WORKER_ID)).thenReturn(existingWorker);
		workerController.createNewWorker(newWorker);
		InOrder inOrder = inOrder(workerRepository, workerView);
		inOrder.verify(workerView).showError("Worker with id " + newWorker.getWorkerId() + " Already exists",
				existingWorker);
		verifyNoMoreInteractions(ignoreStubs(workerRepository));
	}

	@Test
	public void testDeleteWorkerWhenWorkerIsNull() {
		try {
			workerController.deleteWorker(null);
			fail("Expected an NullPointerException to be thrown ");
		} catch (NullPointerException e) {
			assertEquals("Worker cannot be null", e.getMessage());
		}
		verifyNoMoreInteractions(workerRepository);
		verifyNoMoreInteractions(workerView);
	}

	@Test
	public void testDeleteWorkerWhenWorkerAlreadyExistsWithOrders() {
		List<CustomerOrder> ordersList = asList(new CustomerOrder());
		Worker workerToBeDeleted = new Worker(DEFAULT_WORKER_ID, DEFAULT_WORKER_NAME, ordersList);
		when(workerRepository.findById(DEFAULT_WORKER_ID)).thenReturn(workerToBeDeleted);
		workerController.deleteWorker(DEFAULT_WORKER_ID);
		InOrder inOrder = inOrder(workerRepository, workerView);
		inOrder.verify(workerView).showError(
				"This worker has " + ordersList.size() + " orders cannot delete worker with assigned orders",
				workerToBeDeleted);
		verifyNoMoreInteractions(ignoreStubs(workerRepository));

	}

	@Test
	public void testDeleteWorkerWhenWorkerAlreadyExistsEmptyOrders() {
		Worker workerToBeDeleted = new Worker(DEFAULT_WORKER_ID, DEFAULT_WORKER_NAME, Collections.emptyList());
		when(workerRepository.findById(DEFAULT_WORKER_ID)).thenReturn(workerToBeDeleted);
		workerController.deleteWorker(DEFAULT_WORKER_ID);
		InOrder inOrder = inOrder(workerRepository, workerView);
		inOrder.verify(workerRepository).delete(workerToBeDeleted);
		inOrder.verify(workerView).workerRemoved(workerToBeDeleted);
		verifyNoMoreInteractions(ignoreStubs(workerRepository));

	}

	@Test
	public void testDeleteWorkerWhenWorkerAlreadyExistsNullOrders() {
		Worker workerToBeDeleted = WORKER_WITH_No_ORDERS;
		when(workerRepository.findById(DEFAULT_WORKER_ID)).thenReturn(workerToBeDeleted);
		workerController.deleteWorker(DEFAULT_WORKER_ID);
		InOrder inOrder = inOrder(workerRepository, workerView);
		inOrder.verify(workerRepository).delete(workerToBeDeleted);
		inOrder.verify(workerView).workerRemoved(workerToBeDeleted);
		verifyNoMoreInteractions(ignoreStubs(workerRepository));
	}

	@Test
	public void testDeleteWorkerWhenWorkerNotExists() {
		Worker workerToBeDeleted = WORKER_WITH_No_ORDERS;
		when(workerRepository.findById(DEFAULT_WORKER_ID)).thenReturn(null);
		workerController.deleteWorker(DEFAULT_WORKER_ID);
		InOrder inOrder = inOrder(workerRepository, workerView);
		inOrder.verify(workerView)
				.showErrorWorkerNotFound("No worker Exists with id " + workerToBeDeleted.getWorkerId(), null);
		verifyNoMoreInteractions(ignoreStubs(workerRepository));

	}

}
