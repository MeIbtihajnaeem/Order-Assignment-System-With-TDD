package com.example.orderAssignmentSystem.controller;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.ignoreStubs;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.orderAssignmentSystem.model.Category;
import com.example.orderAssignmentSystem.repository.CategoryRepository;
import com.example.orderAssignmentSystem.view.CategoryView;

public class CategoryControllerTest {

	@Mock
	private CategoryRepository categoryRepository;

	@Mock
	private CategoryView categoryView;

	@InjectMocks
	private CategoryController categoryController;

	private AutoCloseable closeable;

	@Before
	public void setup() {
		closeable = MockitoAnnotations.openMocks(this);
	}

	@After
	public void releaseMocks() throws Exception {
		closeable.close();
	}

	@Test
	public void testAllCategories() {
		List<Category> categories = asList(new Category());
		when(categoryRepository.findAll()).thenReturn(categories);
		categoryController.allCategories();
		verify(categoryView).showAllCategories(categories);
	}

	@Test
	public void testAllCategoriesEmptyList() {
		when(categoryRepository.findAll()).thenReturn(Collections.emptyList());
		categoryController.allCategories();
		verify(categoryView).showAllCategories(Collections.emptyList());
	}

	@Test
	public void testAllCategoriesNullList() {
		when(categoryRepository.findAll()).thenReturn(null);
		categoryController.allCategories();
		verify(categoryView).showAllCategories(null);

	}

	@Test
	public void testNewCategoryNullCategory() {
		try {
			categoryController.newCategory(null);
			fail("Expected an NullPointerException to be thrown ");
		} catch (NullPointerException e) {
			assertEquals("Category cannot be null", e.getMessage());
		}
		verifyNoMoreInteractions(categoryRepository);
		verifyNoMoreInteractions(categoryView);
	}

	private static final Category DEFAULT_CATEGORY = new Category("1", "Plumber");

	@Test
	public void testNewCategoryWhenCategoryNotAlreadyExist() {
		Category category = DEFAULT_CATEGORY;
		when(categoryRepository.findByName("Plumber")).thenReturn(null);
		categoryController.newCategory(category);
		InOrder inOrder = inOrder(categoryRepository, categoryView);
		inOrder.verify(categoryRepository).save(category);
		inOrder.verify(categoryView).categoryAdded(category);
		verifyNoMoreInteractions(ignoreStubs(categoryRepository));

	}

	@Test
	public void testNewCategoryWhenCategoryAlreadyExist() {
		Category categoryToAdd = DEFAULT_CATEGORY;
		Category existingCategory = new Category("2", "Plumber");
		when(categoryRepository.findByName("Plumber")).thenReturn(existingCategory);
		categoryController.newCategory(categoryToAdd);
		InOrder inOrder = inOrder(categoryRepository, categoryView);
		inOrder.verify(categoryView).showError("Already existing category with name" + categoryToAdd.getCategoryName(),
				existingCategory);
		verifyNoMoreInteractions(ignoreStubs(categoryRepository));
	}

	@Test
	public void testDeleteCategoryWhenNullCategory() {
		try {
			categoryController.deleteCategory(null);
			fail("Expected an NullPointerException to be thrown ");
		} catch (NullPointerException e) {
			assertEquals("Category cannot be null", e.getMessage());
		}
		verifyNoMoreInteractions(categoryRepository);
		verifyNoMoreInteractions(categoryView);

	}

	@Test
	public void testDeleteCategoryWhenCategoryExist() {
		Category category = DEFAULT_CATEGORY;
		when(categoryRepository.findById("1")).thenReturn(category);
		categoryController.deleteCategory(category);
		InOrder inOrder = inOrder(categoryRepository, categoryView);
		inOrder.verify(categoryRepository).delete(category.getCategoryId());
		inOrder.verify(categoryView).categoryRemoved(category);
		verifyNoMoreInteractions(ignoreStubs(categoryRepository));

	}

	@Test
	public void testDeleteCategoryWhenCategoryNotExist() {
		Category category = DEFAULT_CATEGORY;
		when(categoryRepository.findById("1")).thenReturn(null);
		categoryController.deleteCategory(category);
		InOrder inOrder = inOrder(categoryRepository, categoryView);
		inOrder.verify(categoryView).showErrorCategoryNotFound("No Category exists with id " + category.getCategoryId(),
				null);
		verifyNoMoreInteractions(ignoreStubs(categoryRepository));
	}

}
