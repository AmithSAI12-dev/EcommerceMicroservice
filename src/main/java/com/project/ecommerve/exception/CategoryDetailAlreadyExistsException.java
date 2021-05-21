package com.project.ecommerve.exception;

public class CategoryDetailAlreadyExistsException extends Exception {
  // This Exception is thrown when category already exists
  public CategoryDetailAlreadyExistsException(final String message) {
    super(message);
  }
}
