package com.project.ecommerve.exception;

public class ProductDetailDoesNotExistsException extends Exception {
  // This Exception is thrown when the product does not exists
  public ProductDetailDoesNotExistsException(final String message) {
    super(message);
  }
}
