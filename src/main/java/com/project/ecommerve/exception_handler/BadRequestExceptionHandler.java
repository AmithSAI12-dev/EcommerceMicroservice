package com.project.ecommerve.exception_handler;

import java.time.ZonedDateTime;

import lombok.NoArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.project.ecommerve.dto.ExceptionFormat;
import com.project.ecommerve.exception.*;

@RestControllerAdvice
@NoArgsConstructor
public class BadRequestExceptionHandler {

  @ExceptionHandler({
    BrandDetailDoesNotExistsException.class,
    BrandDetailAlreadyExistsException.class,
    CategoryDetailDoesNotExistsException.class,
    CategoryDetailAlreadyExistsException.class,
    ProductDetailDoesNotExistsException.class,
    TagAlreadyExistsException.class,
    TagDoesNotExistsException.class
  })
  public ResponseEntity<ExceptionFormat> handleException(final Exception e) {
    return new ResponseEntity<>(
        new ExceptionFormat(e.getMessage(), HttpStatus.BAD_REQUEST, ZonedDateTime.now()),
        HttpStatus.BAD_REQUEST);
  }
}
