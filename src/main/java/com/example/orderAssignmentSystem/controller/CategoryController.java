package com.example.orderAssignmentSystem.controller;

import java.util.List;

import com.example.orderAssignmentSystem.model.Category;
import com.example.orderAssignmentSystem.repository.CategoryRepository;

public class CategoryController {
	private List<Category> categories;

	private CategoryRepository categoryRepository;

	public CategoryController(List<Category> categories, CategoryRepository categoryRepository) {
		super();
		this.categories = categories;
		this.categoryRepository = categoryRepository;
	}

}
