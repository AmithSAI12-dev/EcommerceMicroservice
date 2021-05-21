package com.project.ecommerve.exception_handler;

import java.time.ZonedDateTime;

import lombok.NoArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.project.ecommerve.dto.ExceptionFormat;
import com.project.ecommerve.exception.NoBrandsAvailableException;
import com.project.ecommerve.exception.NoCategoryAvailableException;
import com.project.ecommerve.exception.NoProductAvailableException;
import com.project.ecommerve.exception.NoTagsAvailableException;

@RestControllerAdvice
@NoArgsConstructor
public class NoDataAvailableExceptionHandler {

  @ExceptionHandler(
      value = {
        NoBrandsAvailableException.class,
        NoCategoryAvailableException.class,
        NoProductAvailableException.class,
        NoTagsAvailableException.class
      })
  public ResponseEntity<ExceptionFormat> handleException(final Exception e) {
    return new ResponseEntity<>(
        new ExceptionFormat(e.getMessage(), HttpStatus.NO_CONTENT, ZonedDateTime.now()),
        HttpStatus.NO_CONTENT);
  }
}
