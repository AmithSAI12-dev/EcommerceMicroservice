package com.project.ecommerve.exception;

public class ProductDoesNotExistsException extends Exception {
  // This Exception is thrown when the product does not exists
  public ProductDoesNotExistsException(String message) {
    super(message);
  }
}
