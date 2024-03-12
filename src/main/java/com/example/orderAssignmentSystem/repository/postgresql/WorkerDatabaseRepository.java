package com.example.orderAssignmentSystem.repository.postgresql;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import com.example.orderAssignmentSystem.model.Worker;
import com.example.orderAssignmentSystem.repository.WorkerRepository;

public class WorkerDatabaseRepository implements WorkerRepository {

	private EntityManager entityManager;

	public WorkerDatabaseRepository(EntityManager entityManager) {
		super();
		this.entityManager = entityManager;
	}

	@Override
	public List<Worker> findAll() {

		return entityManager.createQuery("SELECT w FROM Worker w", Worker.class).getResultList();
	}

	@Override
	public Worker findById(Long id) {
		return entityManager.find(Worker.class, id);
	}

	@Override
	public Worker save(Worker worker) {
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		Worker newWorker = entityManager.merge(worker);
		transaction.commit();
		return newWorker;
	}

	@Override
	public void delete(Worker worker) {
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		entityManager.remove(worker);
		transaction.commit();
	}

	@Override
	public Worker findByCodiceFiscale(String codiceFiscale) {
		String jpql = "SELECT w FROM Worker w WHERE w.codiceFiscale = :codiceFiscale";
		TypedQuery<Worker> query = entityManager.createQuery(jpql, Worker.class);
		query.setParameter("codiceFiscale", codiceFiscale);
		try {
			return query.getSingleResult();
		} catch (NoResultException ex) {
			return null;
		}
	}

}
