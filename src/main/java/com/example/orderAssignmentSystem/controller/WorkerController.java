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
		workerView.showAllWorkers(workerRepository.findAll());
	}

	public void createNewWorker(Worker worker) {
		Objects.requireNonNull(worker, "Worker is null");
		Objects.requireNonNull(worker.getWorkerName(), "Worker name is null");
		Objects.requireNonNull(worker.getCodiceFiscale(), "Worker fiscal code is null");
		if (worker.getWorkerName().length() > 20) {
			throw new IllegalArgumentException("Worker name cannot be greater than 20");
		}
		if (worker.getCodiceFiscale().length() != 16) {
			throw new IllegalArgumentException("Worker Codice Fiscale must be 16 charachters");
		}

		workerRepository.save(worker);
		workerView.workerAdded(worker);
	}

	public void deleteWorker(Long workerId) {
		Objects.requireNonNull(workerId, "Worker id is null");
		if (workerId <= 0) {
			throw new IllegalArgumentException("Worker id cannot be less than or equal to 0");
		}
		Worker workerExists = workerRepository.findById(workerId);
		if (workerExists == null) {
			workerView.showErrorWorkerNotFound("No Worker found with id " + workerId, workerExists);
			return;
		}
		if (workerExists.getOrders() != null) {
			workerView.showError("Cannot delete worker with orders", workerExists);
			return;
		}

		workerRepository.delete(workerExists);
		workerView.workerRemoved(workerExists);
	}

}
