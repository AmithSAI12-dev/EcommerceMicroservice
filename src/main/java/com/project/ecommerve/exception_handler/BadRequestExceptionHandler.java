package com.project.ecommerve.exception_handler;

import java.time.ZonedDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.project.ecommerve.dto.ExceptionFormat;
import com.project.ecommerve.exception.BrandDetailAlreadyExistsException;
import com.project.ecommerve.exception.BrandDetailDoesNotExistsException;

@RestControllerAdvice
public class BadRequestExceptionHandler {

  @ExceptionHandler(
      value = {BrandDetailDoesNotExistsException.class, BrandDetailAlreadyExistsException.class})
  public ResponseEntity<ExceptionFormat> handleException(Exception e) {
    return new ResponseEntity<>(
        new ExceptionFormat(e.getMessage(), HttpStatus.BAD_REQUEST, ZonedDateTime.now()),
        HttpStatus.BAD_REQUEST);
  }
}
