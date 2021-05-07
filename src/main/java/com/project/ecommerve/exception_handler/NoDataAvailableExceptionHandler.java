package com.project.ecommerve.exception_handler;

import java.time.ZonedDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.project.ecommerve.dto.ExceptionFormat;
import com.project.ecommerve.exception.NoBrandsAvailableException;

@RestControllerAdvice
public class NoDataAvailableExceptionHandler {

  @ExceptionHandler(value = NoBrandsAvailableException.class)
  public ResponseEntity<ExceptionFormat> handleException(Exception e) {
    return new ResponseEntity<>(
        new ExceptionFormat(e.getMessage(), HttpStatus.BAD_REQUEST, ZonedDateTime.now()),
        HttpStatus.BAD_REQUEST);
  }
}
