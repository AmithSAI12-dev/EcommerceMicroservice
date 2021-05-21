package com.project.ecommerve.exception;

public class BrandDetailAlreadyExistsException extends Exception {

  // This Exception is thrown when the brand already exists
  public BrandDetailAlreadyExistsException(final String message) {
    super(message);
  }
}
