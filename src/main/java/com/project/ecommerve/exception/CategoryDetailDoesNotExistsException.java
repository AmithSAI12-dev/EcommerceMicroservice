package com.project.ecommerve.exception;

public class CategoryDetailDoesNotExistsException extends Exception {
  // This Exception is thrown if category does not exists
  public CategoryDetailDoesNotExistsException(String message) {
    super(message);
  }
}
