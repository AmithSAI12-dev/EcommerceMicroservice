package com.project.ecommerve.exception;

public class TagDoesNotExistsException extends Exception {
  // This Exception is thrown when tag does not exists
  public TagDoesNotExistsException(final String message) {
    super(message);
  }
}
