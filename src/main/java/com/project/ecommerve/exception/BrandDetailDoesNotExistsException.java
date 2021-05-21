package com.project.ecommerve.exception;

public class BrandDetailDoesNotExistsException extends Exception {

  // This Exception is thrown when Brand Detail does not exists
  public BrandDetailDoesNotExistsException(final String message) {
    super(message);
  }
}
