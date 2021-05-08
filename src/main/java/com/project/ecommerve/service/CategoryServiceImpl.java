package com.project.ecommerve.service;

import com.project.ecommerve.constants.ExceptionMessage;
import com.project.ecommerve.dto.CategoryDto;
import com.project.ecommerve.exception.CategoryDetailAlreadyExistsException;
import com.project.ecommerve.exception.CategoryDetailDoesNotExistsException;
import com.project.ecommerve.exception.NoCategoryAvailableException;
import com.project.ecommerve.model.Category;
import com.project.ecommerve.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService{

    private final CategoryRepository categoryRepository;
    private final String SUCCESS_MESSAGE = "Successfully Deleted Category";

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<Category> retrieveAllCategories(Integer page, Integer size, String sortBy) throws NoCategoryAvailableException {
        // Fetching Category from database, Note: Pagination and Sorting is used
        Page<Category> categoryPage = categoryRepository.findAll(PageRequest.of(page, size, Sort.by(sortBy)));
        if (categoryPage.isEmpty()) {
            // If No Category exists then throwing Exception
            throw new NoCategoryAvailableException(ExceptionMessage.NO_CATEGORY_AVAILABLE);
        }
        // Returning category list
        return categoryPage.getContent();
    }

    @Override
    public Category persistCategory(Category category) throws CategoryDetailAlreadyExistsException {
        if (categoryRepository.findById(category.getName()).isPresent()) {
            // If Category already exists, then throwing Exception
            throw new CategoryDetailAlreadyExistsException(ExceptionMessage.CATEGORY_ALREADY_EXISTS);
        }
        // Returning category
        return categoryRepository.save(category);
    }

    @Override
    public Category updateCategory(Category category) throws CategoryDetailDoesNotExistsException {
        if (categoryRepository.findById(category.getName()).isEmpty()) {
            // If Category Does not exists then throwing Exception
            throw new CategoryDetailDoesNotExistsException(ExceptionMessage.CATEGORY_DOES_NOT_EXISTS);
        }
        // Returning Category
        return categoryRepository.save(category);
    }

    @Override
    public CategoryDto deleteCategory(String name) throws CategoryDetailDoesNotExistsException {
        Optional<Category> optionalCategory = categoryRepository.findById(name);
        if (optionalCategory.isEmpty()) {
            // If Category Does not exists then throwing Exception
            throw new CategoryDetailDoesNotExistsException(ExceptionMessage.CATEGORY_DOES_NOT_EXISTS);
        }
        // Returning Category Dto
        return new CategoryDto(
                optionalCategory.get().getName(),
                optionalCategory.get().getDescription(),
                optionalCategory.get().getImageUrl(),
                SUCCESS_MESSAGE
        );
    }
}
