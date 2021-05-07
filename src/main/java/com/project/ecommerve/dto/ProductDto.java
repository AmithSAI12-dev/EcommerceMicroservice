package com.project.ecommerve.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
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
}
