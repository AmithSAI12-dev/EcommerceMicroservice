package com.project.ecommerve.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.project.ecommerve.dto.CategoryDto;
import com.project.ecommerve.exception.CategoryDetailAlreadyExistsException;
import com.project.ecommerve.exception.CategoryDetailDoesNotExistsException;
import com.project.ecommerve.exception.NoCategoryAvailableException;
import com.project.ecommerve.model.Category;
import com.project.ecommerve.service.CategoryService;

@RestController
@RequestMapping("/category")
@CrossOrigin("http://localhost:4200")
public class CategoryController {

  private final CategoryService categoryService;

  @Autowired
  public CategoryController(final CategoryService categoryService) {
    this.categoryService = categoryService;
  }

  @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<Category>> getAllCategories(
      @RequestParam(defaultValue = "0", required = false) final Integer page,
      @RequestParam(defaultValue = "10", required = false) final Integer size,
      @RequestParam(defaultValue = "name", required = false) final String sortBy)
      throws NoCategoryAvailableException {
    final List<Category> categories = categoryService.retrieveAllCategories(page, size, sortBy);
    return new ResponseEntity<>(categories, HttpStatus.OK);
  }

  @PostMapping(value = "/add", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Category> addCategory(@RequestBody final CategoryDto categoryDto)
      throws CategoryDetailAlreadyExistsException {
    final Category category = categoryService.persistCategory(new Category(categoryDto));
    return new ResponseEntity<>(category, HttpStatus.CREATED);
  }

  @PutMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Category> updateCategory(@RequestBody final CategoryDto categoryDto)
      throws CategoryDetailDoesNotExistsException {
    final Category category = categoryService.updateCategory(new Category(categoryDto));
    return new ResponseEntity<>(category, HttpStatus.CREATED);
  }

  @DeleteMapping(value = "/delete", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<CategoryDto> deleteCategory(@RequestParam final String name)
      throws CategoryDetailDoesNotExistsException {
    final CategoryDto categoryDto = categoryService.deleteCategory(name);
    return new ResponseEntity<>(categoryDto, HttpStatus.OK);
  }
}
