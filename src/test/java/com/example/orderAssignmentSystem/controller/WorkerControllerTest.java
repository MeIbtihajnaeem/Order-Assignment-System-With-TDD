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
	public void testAllWorkerMethodWhenWorker() {
		List<Worker> worker = asList(new Worker());
		when(workerRepository.findAll()).thenReturn(worker);
		workerController.getAllWorkers();
		verify(workerView).showAllWorkers(worker);
	}

	@Test
	public void testAllWorkerMethodWhenEmptyList() {
		when(workerRepository.findAll()).thenReturn(Collections.emptyList());
		workerController.getAllWorkers();
		verify(workerView).showAllWorkers(Collections.emptyList());
	}

	@Test
	public void testAllWorkerMethodWhenNullList() {
		when(workerRepository.findAll()).thenReturn(null);
		workerController.getAllWorkers();
		verify(workerView).showAllWorkers(null);
	}

	@Test
	public void testCreateNewWorkerMethodWhenNullWorker() {
		try {
			workerController.createNewWorker(null);
			fail("Expected an NullPointerException to be thrown ");
		} catch (NullPointerException e) {
			assertEquals("Worker is null", e.getMessage());
		}
	}

	@Test
	public void testCreateNewWorkerMethodWhenWorkerIdIsNotNull() {
		Worker worker = new Worker();
		worker.setWorkerId(1l);
		try {
			workerController.createNewWorker(worker);
			fail("Expected an IllegalArgumentException to be thrown ");
		} catch (IllegalArgumentException e) {
			assertEquals("Worker id is not null", e.getMessage());
		}
	}

	@Test
	public void testCreateNewWorkerMethodWhenWorkerNameIsNull() {
		Worker worker = new Worker();
		try {
			workerController.createNewWorker(worker);
			fail("Expected an NullPointerException to be thrown ");
		} catch (NullPointerException e) {
			assertEquals("Worker name is null", e.getMessage());
		}
	}

	@Test
	public void testCreateNewWorkerMethodWhenWorkerNameLengthGreaterThen20() {
		Worker worker = new Worker();
		worker.setWorkerName("Matteo Moretti Matteo Moretti");
		worker.setCategory(CategoryEnum.PLUMBER);
		try {
			workerController.createNewWorker(worker);
			fail("Expected an IllegalArgumentException to be thrown ");
		} catch (IllegalArgumentException e) {
			assertEquals("Worker name cannot be greater than 20", e.getMessage());
		}
	}

	@Test
	public void testCreateNewWorkerMethodWhenWorkerNameLengthEqualTo20() {
		Worker worker = new Worker();
		worker.setWorkerName("abcdefaddesadf adsed");
		worker.setCategory(CategoryEnum.PLUMBER);
		try {
			workerController.createNewWorker(worker);
		} catch (IllegalArgumentException e) {
			fail("No IllegalArgumentException should be thrown for exactly 20 characters name");
		}
	}

	@Test
	public void testCreateNewWorkerMethodWhenWorkerCategoryIsNull() {
		Worker worker = new Worker();
		worker.setWorkerName("Matteo Moretti");
		try {
			workerController.createNewWorker(worker);
			fail("Expected an NullPointerException to be thrown ");
		} catch (NullPointerException e) {
			assertEquals("Worker category is null", e.getMessage());
		}
	}

	@Test
	public void testCreateNewWorkerMethod() {
		Worker worker = new Worker();
		worker.setWorkerName("Matteo Moretti");
		worker.setCategory(CategoryEnum.PLUMBER);
		workerController.createNewWorker(worker);
		InOrder inOrder = inOrder(workerView, workerRepository);
		inOrder.verify(workerRepository).save(worker);
		inOrder.verify(workerView).workerAdded(worker);
		verifyNoMoreInteractions(ignoreStubs(workerRepository));
	}

	@Test
	public void testDeleteWorkerMethodWhenWorkerIdNull() {
		try {
			workerController.deleteWorker(null);
			fail("Expected an NullPointerException to be thrown ");
		} catch (NullPointerException e) {
			assertEquals("Worker id is null", e.getMessage());
		}
	}

	@Test
	public void testDeleteWorkerMethodWhenWorkerIdIsZero() {
		Worker worker = new Worker();
		worker.setWorkerId(0l);
		try {
			workerController.deleteWorker(0l);
			fail("Expected an IllegalArgumentException to be thrown ");
		} catch (IllegalArgumentException e) {
			assertEquals("Worker id cannot be less than or equal to 0", e.getMessage());
		}
	}

	@Test
	public void testDeleteWorkerMethodWhenWorkerIdIsNegative() {
		Worker worker = new Worker();
		worker.setWorkerId(-1l);
		try {
			workerController.deleteWorker(-1l);
			fail("Expected an IllegalArgumentException to be thrown ");
		} catch (IllegalArgumentException e) {
			assertEquals("Worker id cannot be less than or equal to 0", e.getMessage());
		}
	}

	@Test
	public void testDeleteWorkerMethodWhenWorkerIdIsGreaterThanZero() {
		Worker worker = new Worker();
		worker.setWorkerId(1l);
		try {
			workerController.deleteWorker(1l);
		} catch (IllegalArgumentException e) {
			fail("No IllegalArgumentException Should be thrown thrown ");
		}
	}

	@Test
	public void testDeleteWorkerMethodWhenWorkerNotExists() {
		Worker worker = new Worker();
		long workerId = 1l;
		worker.setWorkerId(workerId);
		when(workerRepository.findById(workerId)).thenReturn(null);
		workerController.deleteWorker(workerId);
		InOrder inOrder = inOrder(workerView, workerRepository);
		inOrder.verify(workerView).showErrorWorkerNotFound("No Worker found with id " + workerId, null);
		verifyNoMoreInteractions(ignoreStubs(workerRepository));
	}

	@Test
	public void testDeleteWorkerMethodWhenWorkerExistsHasNullOrders() {

		Worker worker = new Worker();
		long workerId = 1l;
		worker.setWorkerId(workerId);

		when(workerRepository.findById(workerId)).thenReturn(worker);
		workerController.deleteWorker(workerId);
		InOrder inOrder = inOrder(workerView, workerRepository);
		inOrder.verify(workerRepository).delete(worker);
		inOrder.verify(workerView).workerRemoved(worker);
		verifyNoMoreInteractions(ignoreStubs(workerRepository));
	}

	@Test
	public void testDeleteWorkerMethodWhenWorkerExistsHasEmptyOrdersList() {

		Worker worker = new Worker();
		long workerId = 1l;
		worker.setWorkerId(workerId);
		worker.setOrders(Collections.emptyList());

		when(workerRepository.findById(workerId)).thenReturn(worker);
		workerController.deleteWorker(workerId);
		InOrder inOrder = inOrder(workerView, workerRepository);
		inOrder.verify(workerView).showError("Cannot delete worker with orders", worker);
		verifyNoMoreInteractions(ignoreStubs(workerRepository));
	}

	@Test
	public void testDeleteWorkerMethodWhenWorkerExistsButHasOrders() {
		CustomerOrder order = new CustomerOrder();

		Worker worker = new Worker();
		long workerId = 1l;
		worker.setWorkerId(workerId);
		worker.setOrders(asList(order));

		when(workerRepository.findById(workerId)).thenReturn(worker);
		workerController.deleteWorker(workerId);
		InOrder inOrder = inOrder(workerView, workerRepository);
		inOrder.verify(workerView).showError("Cannot delete worker with orders", worker);
		verifyNoMoreInteractions(ignoreStubs(workerRepository));
	}

}
