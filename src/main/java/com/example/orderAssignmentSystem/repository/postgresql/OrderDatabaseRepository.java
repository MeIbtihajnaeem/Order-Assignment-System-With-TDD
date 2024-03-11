package com.example.orderAssignmentSystem.repository.postgresql;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.transaction.Transactional;

import com.example.orderAssignmentSystem.model.CustomerOrder;
import com.example.orderAssignmentSystem.repository.OrderRepository;

public class OrderDatabaseRepository implements OrderRepository {

	private EntityManager entityManager;

	public OrderDatabaseRepository(EntityManager entityManager) {
		super();
		this.entityManager = entityManager;
	}

	@Override
	public List<CustomerOrder> findAll() {
		return entityManager.createQuery("SELECT o FROM CustomerOrder o", CustomerOrder.class).getResultList();
	}

	@Override
	public CustomerOrder findById(Long id) {
		return entityManager.find(CustomerOrder.class, id);
	}

	@Override
	@Transactional
	public CustomerOrder save(CustomerOrder order) {
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		CustomerOrder mergedOrder = entityManager.merge(order);
		transaction.commit();
		return mergedOrder;
//		entityManager.merge(order);
	}

	@Override
	@Transactional
	public void delete(CustomerOrder order) {
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		entityManager.remove(order);
		transaction.commit();
	}

}
