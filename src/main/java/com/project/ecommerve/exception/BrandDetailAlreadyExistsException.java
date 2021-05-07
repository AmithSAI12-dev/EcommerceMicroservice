package com.project.ecommerve.exception;

public class BrandDetailAlreadyExistsException extends Exception {

    // This Exception is thrown when the brand already exists
    public BrandDetailAlreadyExistsException(String message) {
        super(message);
    }
}
