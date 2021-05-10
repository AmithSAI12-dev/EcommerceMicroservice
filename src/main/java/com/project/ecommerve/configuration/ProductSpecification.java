package com.project.ecommerve.configuration;

import java.util.List;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;

import org.springframework.data.jpa.domain.Specification;

import com.project.ecommerve.model.Product;

public class ProductSpecification {

  public static Specification<Product> getProductByCategory(List<String> categoryList) {
    return (root, query, criteriaBuilder) -> {
      Join<Object, Object> category = root.join("category");
      Predicate predicate = category.get("name").in(categoryList);
      return predicate;
    };
  }

  public static Specification<Product> getProductByBrand(List<String> brandList) {
    return (root, query, criteriaBuilder) -> {
      Join<Object, Object> brand = root.join("brand");
      Predicate predicate = brand.get("name").in(brandList);
      return predicate;
    };
  }

  public static Specification<Product> getProductByPriceRange(float start, float end) {
    return (root, query, criteriaBuilder) -> {
      return criteriaBuilder.between(root.get("price"), start, end);
    };
  }

  public static Specification<Product> getProductByCategoryAndBrand(
      List<String> categoryList, List<String> brandList) {
    return Specification.where(getProductByCategory(categoryList))
        .and(getProductByBrand(brandList));
  }

  public static Specification<Product> getProductByCategoryAndBrandAndPrice(
      List<String> categoryList, List<String> brandList, float start, float end) {
    return Specification.where(getProductByCategory(categoryList))
        .and(getProductByBrand(brandList))
        .and(getProductByPriceRange(start, end));
  }
}
