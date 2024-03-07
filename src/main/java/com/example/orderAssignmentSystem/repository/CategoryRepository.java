package com.example.orderAssignmentSystem.repository;

import java.util.List;

import com.example.orderAssignmentSystem.model.Category;

public interface CategoryRepository {
	public List<Category> findAll();

	public Category findById(String id);

	public Category findByName(String categoryName);

	public void save(Category category);

	public void delete(String id);
}
