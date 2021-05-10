package com.project.ecommerve.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {

  private String id;
  private String name;
  private double price;
  private String image1;
  private String image2;
  private String image3;
  private String image4;
  private double discount;
  private boolean available;
  private String description;
  private LocalDate localDate;
  private String message;
}
