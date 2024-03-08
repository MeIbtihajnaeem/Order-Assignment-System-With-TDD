package com.example.orderAssignmentSystem.controller;

import static java.util.Arrays.asList;
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

import com.example.orderAssignmentSystem.model.Order;
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

	@Test
	public void testAllworkerWhenOrderListNotEmpty() {
		List<Order> order = asList(new Order());
		List<Worker> workers = asList(new Worker("1", "Alic", order));
		when(workerRepository.findAll()).thenReturn(workers);
		workerController.allWorkers();
		verify(workerView).showAllWorkers(argThat(workerList -> {
			if (workers.get(0).getOrders().isEmpty()) {
				return false;
			}
			return true;
		}));
	}

	@Test
	public void testAllWorkersWhenOrderListEmpty() {
		List<Worker> workers = asList(new Worker("1", "Alic", Collections.emptyList()));
		when(workerRepository.findAll()).thenReturn(workers);
		workerController.allWorkers();
		verify(workerView).showAllWorkers(argThat(workerList -> {
			if (workers.get(0).getOrders().isEmpty()) {
				return true;
			}
			return false;
		}));
	}

	@Test
	public void testAllWorkersWhenOrderNull() {
		List<Worker> workers = asList(new Worker("1", "Alic", null));
		when(workerRepository.findAll()).thenReturn(workers);
		workerController.allWorkers();
		verify(workerView).showAllWorkers(argThat(workerList -> {
			if (workers.get(0).getOrders() == null) {
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
		workerController.createNewWorker(null);
		verifyNoMoreInteractions(workerRepository);
		verify(workerView).showError("Worker cannot be null ", null);
	}

	@Test
	public void testCreateNewWorkerWhenWorkerIdNotAlreadyExists() {
		Worker worker = new Worker("1", "Alic");
		when(workerRepository.findById("1")).thenReturn(null);
		workerController.createNewWorker(worker);
		InOrder inOrder = inOrder(workerRepository, workerView);
		inOrder.verify(workerRepository).save(worker);
		inOrder.verify(workerView).workerAdded(worker);
		verifyNoMoreInteractions(ignoreStubs(workerRepository));
	}

	@Test
	public void testCreateNewWorkerWhenWorkerIdAlreadyExists() {
		Worker worker = new Worker("1", "Alic");
		Worker existingWorker = new Worker("1", "Bob");
		when(workerRepository.findById("1")).thenReturn(existingWorker);
		workerController.createNewWorker(worker);
		InOrder inOrder = inOrder(workerRepository, workerView);
		inOrder.verify(workerView).showError("Worker with id " + worker.getWorkerId() + " Already exists",
				existingWorker);
		verifyNoMoreInteractions(ignoreStubs(workerRepository));
	}

	@Test
	public void testDeleteWorkerWhenWorkerIsNull() {
		workerController.deleteWorker(null);
		verifyNoMoreInteractions(workerRepository);
		verify(workerView).showError("Worker cannot be null ", null);

	}

	@Test
	public void testDeleteWorkerWhenWorkerAlreadyExistsWithOrders() {
		List<Order> order = asList(new Order());
		Worker worker = new Worker("1", "Alic", order);
		when(workerRepository.findById("1")).thenReturn(worker);
		workerController.deleteWorker(worker);
		InOrder inOrder = inOrder(workerRepository, workerView);
		inOrder.verify(workerView).showError(
				"This worker has " + order.size() + " orders cannot delete worker with assigned orders", worker);
		verifyNoMoreInteractions(ignoreStubs(workerRepository));

	}

	@Test
	public void testDeleteWorkerWhenWorkerAlreadyExistsEmptyOrders() {
		Worker worker = new Worker("1", "Alic", Collections.emptyList());
		when(workerRepository.findById("1")).thenReturn(worker);
		workerController.deleteWorker(worker);
		InOrder inOrder = inOrder(workerRepository, workerView);
		inOrder.verify(workerRepository).delete(worker.getWorkerId());
		inOrder.verify(workerView).workerRemoved(worker);
		verifyNoMoreInteractions(ignoreStubs(workerRepository));

	}

	@Test
	public void testDeleteWorkerWhenWorkerAlreadyExistsNullOrders() {
		Worker worker = new Worker("1", "Alic", null);
		when(workerRepository.findById("1")).thenReturn(worker);
		workerController.deleteWorker(worker);
		InOrder inOrder = inOrder(workerRepository, workerView);
		inOrder.verify(workerRepository).delete(worker.getWorkerId());
		inOrder.verify(workerView).workerRemoved(worker);
		verifyNoMoreInteractions(ignoreStubs(workerRepository));
	}

	@Test
	public void testDeleteWorkerWhenWorkerNotExists() {
		Worker worker = new Worker("1", "Alic");
		when(workerRepository.findById("1")).thenReturn(null);
		workerController.deleteWorker(worker);
		InOrder inOrder = inOrder(workerRepository, workerView);
		inOrder.verify(workerView).showErrorWorkerNotFound("No worker Exists with id " + worker.getWorkerId(), null);
		verifyNoMoreInteractions(ignoreStubs(workerRepository));

	}

}
