package com.example.orderAssignmentSystem.model;

import java.util.Objects;

public class Category {
	private String categoryId;
	private String categoryName;

	public Category() {

	}

	public Category(String categoryId, String categoryName) {
		super();
		this.categoryId = categoryId;
		this.categoryName = categoryName;
	}

	public String getCategoryId() {
		return categoryId;
	}

	public String getCategoryName() {
		return categoryName;
	}



}
