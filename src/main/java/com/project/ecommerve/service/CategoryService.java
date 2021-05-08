package com.project.ecommerve.service;

import com.project.ecommerve.dto.CategoryDto;
import com.project.ecommerve.exception.CategoryDetailDoesNotExistsException;
import com.project.ecommerve.exception.CategoryDetailAlreadyExistsException;
import com.project.ecommerve.exception.NoCategoryAvailableException;
import com.project.ecommerve.model.Category;

import java.util.List;

public interface CategoryService {

    public List<Category> retrieveAllCategories(Integer page, Integer size, String sortBy) throws NoCategoryAvailableException;
    public Category persistCategory(Category category) throws CategoryDetailAlreadyExistsException;
    public Category updateCategory(Category category) throws CategoryDetailDoesNotExistsException;
    public CategoryDto deleteCategory(String name) throws CategoryDetailDoesNotExistsException;
}
