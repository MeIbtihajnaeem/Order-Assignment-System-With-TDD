package com.example.orderAssignmentSystem.controller;

import java.util.Objects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.example.orderAssignmentSystem.model.Worker;
import com.example.orderAssignmentSystem.repository.WorkerRepository;
import com.example.orderAssignmentSystem.view.WorkerView;

public class WorkerController {

	private static final Logger LOGGER = LogManager.getLogger(WorkerController.class);
	private WorkerRepository workerRepository;
	private WorkerView workerView;

	public WorkerController(WorkerRepository workerRepository, WorkerView workerView) {
		this.workerRepository = workerRepository;
		this.workerView = workerView;
	}

	public void getAllWorkers() {
		LOGGER.info("Retrieving all workers");
		workerView.showAllWorkers(workerRepository.findAll());
	}

	public void createNewWorker(Worker worker) {
		LOGGER.info("Creating a new worker");
		Objects.requireNonNull(worker, "Worker is null");
		if (worker.getWorkerId() != null) {
			throw new IllegalArgumentException("Worker id is not null");
		}
		Objects.requireNonNull(worker.getWorkerName(), "Worker name is null");
		Objects.requireNonNull(worker.getCategory(), "Worker category is null");
		if (worker.getWorkerName().length() > 20) {
//			throw new IllegalArgumentException("Worker name cannot be greater than 20");
			LOGGER.error("Worker name cannot be greater than 20");
			workerView.showError("Worker name cannot be greater than 20", worker);
			return;
		}

		worker = workerRepository.save(worker);
		workerView.workerAdded(worker);
		LOGGER.info("New worker created: {}", worker);
	}

	public void deleteWorker(Long workerId) {
		LOGGER.info("Deleting worker with ID: {}", workerId);
		Objects.requireNonNull(workerId, "Worker id is null");
		if (workerId <= 0) {
			throw new IllegalArgumentException("Worker id cannot be less than or equal to 0");
		}
		Worker workerExists = workerRepository.findById(workerId);
		if (workerExists == null) {
			LOGGER.error("No Worker found with id {}", workerId);
			workerView.showError("No Worker found with id " + workerId, workerExists);
			return;
		}
		if (workerExists.getOrders() != null) {
			LOGGER.error("Cannot delete worker with orders: {}", workerExists);
			workerView.showError("Cannot delete worker with orders", workerExists);
			return;
		}

		workerRepository.delete(workerExists);
		workerView.workerRemoved(workerExists);
		LOGGER.info("Worker deleted: {}", workerExists);
	}
}
