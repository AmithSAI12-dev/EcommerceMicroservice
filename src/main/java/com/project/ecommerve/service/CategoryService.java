package com.project.ecommerve.service;

import java.util.List;

import com.project.ecommerve.dto.CategoryDto;
import com.project.ecommerve.exception.CategoryDetailAlreadyExistsException;
import com.project.ecommerve.exception.CategoryDetailDoesNotExistsException;
import com.project.ecommerve.exception.NoCategoryAvailableException;
import com.project.ecommerve.model.Category;

public interface CategoryService {

  List<Category> retrieveAllCategories(Integer page, Integer size, String sortBy)
      throws NoCategoryAvailableException;

  Category persistCategory(Category category) throws CategoryDetailAlreadyExistsException;

  Category updateCategory(Category category) throws CategoryDetailDoesNotExistsException;

  CategoryDto deleteCategory(String name) throws CategoryDetailDoesNotExistsException;
}
