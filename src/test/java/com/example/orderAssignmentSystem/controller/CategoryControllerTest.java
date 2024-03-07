package com.example.orderAssignmentSystem.controller;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.orderAssignmentSystem.model.Category;
import com.example.orderAssignmentSystem.repository.CategoryRepository;

public class CategoryControllerTest {

	@Mock
	private List<Category> categories;

	@Mock
	private CategoryRepository categoryRepository;

	@InjectMocks
	private CategoryController categoryController;

	private AutoCloseable closeable;

	@Before
	public void setup() {
		categories = new ArrayList<>();
		closeable = MockitoAnnotations.openMocks(this);
	}

	@After
	public void releaseMocks() throws Exception {
		closeable.close();
	}
}
