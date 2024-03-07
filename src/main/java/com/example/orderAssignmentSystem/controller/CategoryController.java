package com.example.orderAssignmentSystem.controller;

import com.example.orderAssignmentSystem.model.Category;
import com.example.orderAssignmentSystem.repository.CategoryRepository;
import com.example.orderAssignmentSystem.view.CategoryView;

public class CategoryController {

	private CategoryRepository categoryRepository;
	private CategoryView categoryView;

	public CategoryController(CategoryRepository categoryRepository, CategoryView categoryView) {
		this.categoryRepository = categoryRepository;
		this.categoryView = categoryView;
	}

	public void allCategories() {
		categoryView.showAllCategories(categoryRepository.findAll());
	}

	public void newCategory(Category category) {
		if (category != null) {
			Category existingCategory = categoryRepository.findByName(category.getCategoryName());
			if (existingCategory != null) {
				categoryView.showError("Already existing category with name" + category.getCategoryName(),
						existingCategory);
				return;
			}

			categoryRepository.save(category);
			categoryView.categoryAdded(category);
		}
		categoryView.showError("Category cannot be null", null);

	}

	public void deleteCategory(Category category) {
		if (category != null) {
			Category existingCategory = categoryRepository.findById(category.getCategoryId());
			if (existingCategory != null) {
				categoryRepository.delete(category.getCategoryId());
				categoryView.categoryRemoved(category);
				return;
			}
			categoryView.showErrorCategoryNotFound("No Category exists with id " + category.getCategoryId(),
					existingCategory);
		} else {
			categoryView.showError("Category cannot be null", null);

		}

	}

}
