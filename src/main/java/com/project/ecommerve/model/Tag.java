package com.project.ecommerve.model;

import java.util.Set;

import javax.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.ecommerve.dto.TagDto;

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
      cascade = {CascadeType.MERGE},
      fetch = FetchType.LAZY,
      mappedBy = "tags",
      targetEntity = Product.class)
  @JsonIgnore
  private Set<Product> productList;

  public Tag(final TagDto tagDto) {
    this.name = tagDto.getName();
    this.description = tagDto.getDescription();
  }
}
