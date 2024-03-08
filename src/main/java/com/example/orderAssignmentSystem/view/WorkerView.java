package com.example.orderAssignmentSystem.view;

import java.util.List;

import com.example.orderAssignmentSystem.model.Worker;

public interface WorkerView {
	void showAllWorkers(List<Worker> worker);

	void showError(String message, Worker worker);

	void workerAdded(Worker worker);

	void workerRemoved(Worker worker);

	void showErrorWorkerNotFound(String message, Worker worker);
}
