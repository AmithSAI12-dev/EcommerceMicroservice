package com.project.ecommerve.model;

import java.util.List;

import javax.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.ecommerve.dto.CategoryDto;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
public class Category {

  @Id private String name;

  @Column(columnDefinition = "TEXT")
  private String description;

  @Column(columnDefinition = "TEXT")
  private String imageUrl;

  @OneToMany(
      cascade = {CascadeType.PERSIST, CascadeType.MERGE},
      fetch = FetchType.LAZY,
      targetEntity = Product.class,
      mappedBy = "category")
  @JsonIgnore
  private List<Product> productList;

  public Category(final CategoryDto categoryDto) {
    this.name = categoryDto.getName();
    this.description = categoryDto.getDescription();
    this.imageUrl = categoryDto.getImageUrl();
  }

  public Category(final String name) {
    this.name = name;
  }
}
