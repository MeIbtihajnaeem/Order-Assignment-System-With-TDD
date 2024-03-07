package com.example.orderAssignmentSystem.view;

import java.util.List;

import com.example.orderAssignmentSystem.model.Category;

public interface CategoryView {

	void showAllCategories(List<Category> categories);

	void showError(String message, Category category);

	void categoryAdded(Category category);

	void categoryRemoved(Category category);

	void showErrorCategoryNotFound(String message, Category category);
}
