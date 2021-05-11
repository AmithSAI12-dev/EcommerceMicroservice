package com.project.ecommerve.model;

import java.time.LocalDate;
import java.util.Set;

import javax.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.hibernate.annotations.GenericGenerator;

import com.project.ecommerve.configuration.ProductIdGeneratorConfig;
import com.project.ecommerve.dto.ProductDto;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
public class Product {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ProductID")
  @GenericGenerator(
      name = "ProductID",
      strategy = "com.project.ecommerve.configuration.ProductIdGeneratorConfig",
      parameters = {
        @org.hibernate.annotations.Parameter(
            name = ProductIdGeneratorConfig.INCREMENT_PARAM,
            value = "1"),
        @org.hibernate.annotations.Parameter(
            name = ProductIdGeneratorConfig.VALUE_PREFIX_FORMAT,
            value = "PD_"),
        @org.hibernate.annotations.Parameter(
            name = ProductIdGeneratorConfig.NUMBER_FORMAT_PARAMETER,
            value = "%5d")
      })
  private String id;

  private String name;

  @Column(columnDefinition = "TEXT")
  private String description;

  private double price;
  private double discount;

  @Column(columnDefinition = "TEXT")
  private String image1;

  @Column(columnDefinition = "TEXT")
  private String image2;

  @Column(columnDefinition = "TEXT")
  private String image3;

  @Column(columnDefinition = "TEXT")
  private String image4;

  @Column(columnDefinition = "BOOLEAN DEFAULT TRUE")
  private boolean available = true;

  private LocalDate createDate;

  @ManyToOne(
      cascade = {CascadeType.MERGE, CascadeType.PERSIST},
      fetch = FetchType.LAZY,
      targetEntity = Brand.class)
  @JoinColumn(name = "brandName", referencedColumnName = "name")
  private Brand brand;

  @ManyToOne(
      cascade = {CascadeType.MERGE, CascadeType.PERSIST},
      fetch = FetchType.LAZY,
      targetEntity = Category.class)
  @JoinColumn(name = "categoryName", referencedColumnName = "name")
  private Category category;

  @ManyToMany(
      cascade = {CascadeType.MERGE, CascadeType.PERSIST},
      fetch = FetchType.LAZY,
      targetEntity = Tag.class)
  @JoinTable(
      name = "product_tags",
      joinColumns = @JoinColumn(name = "id"),
      inverseJoinColumns = @JoinColumn(name = "name"))
  private Set<Tag> tags;

  public Product(ProductDto productDto) {
    this.name = productDto.getName();
    this.description = productDto.getDescription();
    this.createDate =
        productDto.getLocalDate() != null ? productDto.getLocalDate() : LocalDate.now();
    this.discount = productDto.getDiscount();
    this.image1 = productDto.getImage1();
    this.image2 = productDto.getImage2();
    this.image3 = productDto.getImage3();
    this.image4 = productDto.getImage4();
    this.price = productDto.getPrice();
    this.category = new Category(productDto.getCategory());
    this.brand = new Brand(productDto.getBrand());
  }
}
