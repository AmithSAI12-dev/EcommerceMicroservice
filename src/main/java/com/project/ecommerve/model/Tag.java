package com.project.ecommerve.model;

import java.util.Set;

import javax.persistence.*;

import com.project.ecommerve.dto.TagDto;
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

  public Tag(TagDto tagDto) {
    this.name = tagDto.getName();
    this.description = tagDto.getDescription();
  }
}
