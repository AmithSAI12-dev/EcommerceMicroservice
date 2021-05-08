package com.project.ecommerve.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.project.ecommerve.dto.CategoryDto;
import com.project.ecommerve.exception.CategoryDetailAlreadyExistsException;
import com.project.ecommerve.exception.CategoryDetailDoesNotExistsException;
import com.project.ecommerve.exception.NoCategoryAvailableException;
import com.project.ecommerve.model.Category;
import com.project.ecommerve.service.CategoryService;

@RestController
@RequestMapping(value = "/category")
public class CategoryController {

  private final CategoryService categoryService;

  @Autowired
  public CategoryController(CategoryService categoryService) {
    this.categoryService = categoryService;
  }

  @GetMapping(value = "/")
  public ResponseEntity<List<Category>> getAllCategories(
      @RequestParam(defaultValue = "0", required = false) Integer page,
      @RequestParam(defaultValue = "10", required = false) Integer size,
      @RequestParam(defaultValue = "name", required = false) String sortBy)
      throws NoCategoryAvailableException {
    List<Category> categories = categoryService.retrieveAllCategories(page, size, sortBy);
    return new ResponseEntity<>(categories, HttpStatus.OK);
  }

  @PostMapping(value = "/add")
  public ResponseEntity<Category> addCategory(@RequestBody CategoryDto categoryDto)
      throws CategoryDetailAlreadyExistsException {
    Category category = categoryService.persistCategory(new Category(categoryDto));
    return new ResponseEntity<>(category, HttpStatus.CREATED);
  }

  @PutMapping(value = "/update")
  public ResponseEntity<Category> updateCategory(@RequestBody CategoryDto categoryDto)
      throws CategoryDetailDoesNotExistsException {
    Category category = categoryService.updateCategory(new Category(categoryDto));
    return new ResponseEntity<>(category, HttpStatus.CREATED);
  }

  @DeleteMapping(value = "/delete")
  public ResponseEntity<CategoryDto> deleteCategory(@RequestParam String name)
      throws CategoryDetailDoesNotExistsException {
    CategoryDto categoryDto = categoryService.deleteCategory(name);
    return new ResponseEntity<>(categoryDto, HttpStatus.OK);
  }
}
