package com.example.orderAssignmentSystem.repository;

import java.util.List;

import com.example.orderAssignmentSystem.model.Worker;

public interface WorkerRepository {
	public List<Worker> findAll();


	public Worker findById(Long workerId);

	public Worker save(Worker worker);

	public void delete(Worker worker);
}
