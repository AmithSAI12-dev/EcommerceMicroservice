package com.project.ecommerve.model;

import java.util.List;

import javax.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.ecommerve.dto.BrandDto;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
public class Brand {

  @Id private String name;

  @Column(columnDefinition = "TEXT")
  private String description;

  @Column(columnDefinition = "TEXT")
  private String imageUrl;

  @OneToMany(
      cascade = {CascadeType.PERSIST, CascadeType.MERGE},
      fetch = FetchType.LAZY,
      targetEntity = Product.class,
      mappedBy = "brand")
  @JsonIgnore
  private List<Product> productList;

  public Brand(BrandDto brandDto) {
    this.name = brandDto.getName();
    this.description = brandDto.getDescription();
    this.imageUrl = brandDto.getImageUrl();
  }

  public Brand(String name) {
    this.name = name;
  }
}
