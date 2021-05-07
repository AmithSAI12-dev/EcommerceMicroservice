package com.project.ecommerve.dto;

import java.time.ZonedDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import org.springframework.http.HttpStatus;

@Getter
@Setter
@AllArgsConstructor
public class ExceptionFormat {

  private String message;
  private HttpStatus httpStatus;
  private ZonedDateTime zonedDateTime;
}
