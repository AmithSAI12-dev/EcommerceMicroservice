package com.project.ecommerve.model;

import java.util.Set;

import javax.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
public class Tag {

  @Id private String name;

  @Column(columnDefinition = "TEXT")
  private String description;

  @ManyToMany(
      cascade = {CascadeType.PERSIST, CascadeType.MERGE},
      fetch = FetchType.LAZY,
      mappedBy = "tags",
      targetEntity = Product.class)
  private Set<Product> productList;
}
