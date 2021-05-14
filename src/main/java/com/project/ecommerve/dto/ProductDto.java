package com.project.ecommerve.dto;

import java.time.LocalDate;
import java.util.List;

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
  private List<String> images;
  private double discount;
  private boolean available;
  private String description;
  private LocalDate localDate;
  private String category;
  private String brand;
  private String message;
}
