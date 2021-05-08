package com.project.ecommerve.exception;

public class NoCategoryAvailableException extends Exception {

  // This Exception is thrown when there is no data available in the database
  public NoCategoryAvailableException(String message) {
    super(message);
  }
}
