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

	@Override
	public int hashCode() {
		return Objects.hash(categoryName);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Category other = (Category) obj;
		return Objects.equals(categoryName.toLowerCase(), other.categoryName.toLowerCase());
	}

}
