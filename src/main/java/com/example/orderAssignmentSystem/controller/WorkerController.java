package com.example.orderAssignmentSystem.controller;

import com.example.orderAssignmentSystem.model.Worker;
import com.example.orderAssignmentSystem.repository.WorkerRepository;
import com.example.orderAssignmentSystem.view.WorkerView;

public class WorkerController {

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
		if (worker != null) {
			Worker existingWorker = workerRepository.findById(worker.getWorkerId());
			if (existingWorker != null) {
				workerView.showError("Worker with id " + worker.getWorkerId() + " Already exists", existingWorker);
				return;
			}
			workerRepository.save(worker);
			workerView.workerAdded(worker);
		}
		workerView.showError("Worker cannot be null ", worker);

	}

	public void deleteWorker(Worker worker) {
		if (worker != null) {
			Worker existingWorker = workerRepository.findById(worker.getWorkerId());
			if (existingWorker != null) {

				if (existingWorker.getOrders() == null) {
					workerRepository.delete(worker.getWorkerId());
					workerView.workerRemoved(worker);
					return;
				} else if (existingWorker.getOrders().isEmpty()) {
					workerRepository.delete(worker.getWorkerId());
					workerView.workerRemoved(worker);
					return;
				} else {
					workerView.showError("This worker has " + existingWorker.getOrders().size()
							+ " orders cannot delete worker with assigned orders", worker);

				}
			}
			workerView.showErrorWorkerNotFound("No worker Exists with id " + worker.getWorkerId(), existingWorker);
		}
		workerView.showError("Worker cannot be null ", worker);
	}

}
