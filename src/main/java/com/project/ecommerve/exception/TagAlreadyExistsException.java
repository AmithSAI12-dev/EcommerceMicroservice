package com.project.ecommerve.exception;

public class TagAlreadyExistsException extends Exception {
  // This Exception is thrown if the tag already exists
  public TagAlreadyExistsException(final String message) {
    super(message);
  }
}
