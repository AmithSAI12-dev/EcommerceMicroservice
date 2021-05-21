package com.project.ecommerve.exception;

public class NoProductAvailableException extends Exception {

  // This Exception is thrown when there is no data in the database
  public NoProductAvailableException(final String message) {
    super(message);
  }
}
