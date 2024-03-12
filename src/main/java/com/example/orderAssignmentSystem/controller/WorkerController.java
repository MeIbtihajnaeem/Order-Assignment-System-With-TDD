package com.example.orderAssignmentSystem.controller;

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

	public void allWorkers() {
		workerView.showAllWorkers(workerRepository.findAll());
	}

	public void createNewWorker(Worker worker) {
		if (worker == null) {
			LOGGER.error("ERROR: Worker cannot be null (at createNewWorker method)");
			throw new NullPointerException("Worker cannot be null");
		}
		if (worker.getCodiceFiscale() != null) {
			Worker existingWorker = workerRepository.findByCodiceFiscale(worker.getCodiceFiscale());

			if (existingWorker != null) {
				LOGGER.info("INFO: Worker with " + worker.getWorkerId() + " found  (at createNewWorker method)");
				LOGGER.error(
						"ERROR: Therefore new worker with this id cannot be created!  (at createNewWorker method)");
				workerView.showError("Worker with id " + worker.getWorkerId() + " Already exists", existingWorker);
				return;
			}
		}

		Worker newWorker = workerRepository.save(worker);
		LOGGER.debug("DEBUG: New Worker created!  (at createNewWorker method)");
		workerView.workerAdded(worker);

	}

	public void deleteWorker(Long workerId) {
		if (workerId == null) {
			LOGGER.error("ERROR: Worker cannot be null (at deleteWorker method)");
			throw new NullPointerException("Worker cannot be null");
		}
		Worker existingWorker = workerRepository.findById(workerId);
		if (existingWorker != null) {
			LOGGER.info("INFO: Worker with " + existingWorker.getWorkerId() + " found (at deleteWorker method)");
			if (existingWorker.getOrders() == null) {

				workerRepository.delete(existingWorker);
				LOGGER.debug("DEBUG: Worker deleted sucessfully since no orders found (at deleteWorker method)");
				workerView.workerRemoved(existingWorker);
				return;
			} else if (existingWorker.getOrders().isEmpty()) {
				workerRepository.delete(existingWorker);
				LOGGER.debug("DEBUG: Worker deleted sucessfully since no orders found (at deleteWorker method)");
				workerView.workerRemoved(existingWorker);
				return;
			} else {
				LOGGER.error(
						"ERROR: This worker has " + existingWorker.getOrders().size()
								+ " orders cannot delete worker with assigned orders  (at deleteWorker method)",
						existingWorker);

				workerView.showError("This worker has " + existingWorker.getOrders().size()
						+ " orders cannot delete worker with assigned orders", existingWorker);

			}
		}
		workerView.showErrorWorkerNotFound("No worker Exists with id " + workerId, existingWorker);
		LOGGER.error("ERROR: No worker Exists with id (at deleteWorker method)" + workerId, existingWorker);

	}

}
