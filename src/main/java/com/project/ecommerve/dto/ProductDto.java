package com.project.ecommerve.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import com.project.ecommerve.model.Tag;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {

  private String id;
  private String name;
  private double price;
  private List<String> images;
  private List<String> size;
  private List<Tag> tags;
  private double discount;
  private boolean available;
  private String description;
  private LocalDate localDate;
  private String category;
  private String brand;
  private String message;
}
