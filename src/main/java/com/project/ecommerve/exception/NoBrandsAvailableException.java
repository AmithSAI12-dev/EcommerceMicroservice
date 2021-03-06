package com.project.ecommerve.exception;

public class NoBrandsAvailableException extends Exception {

  // This Exception is thrown when there is no data available in the database
  public NoBrandsAvailableException(final String message) {
    super(message);
  }
}
