package com.project.ecommerve.configuration;

import java.util.List;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;

import org.springframework.data.jpa.domain.Specification;

import com.project.ecommerve.model.Product;

public class ProductSpecification {

  public static Specification<Product> getProductByCategory(final List<String> categoryList) {
    return (root, query, criteriaBuilder) -> {
      final Join<Object, Object> category = root.join("category");
      final Predicate predicate = category.get("name").in(categoryList);
      return predicate;
    };
  }

  public static Specification<Product> getProductByBrand(final List<String> brandList) {
    return (root, query, criteriaBuilder) -> {
      final Join<Object, Object> brand = root.join("brand");
      final Predicate predicate = brand.get("name").in(brandList);
      return predicate;
    };
  }

  public static Specification<Product> getProductByPriceRange(final float start, final float end) {
    return (root, query, criteriaBuilder) -> {
      return criteriaBuilder.between(root.get("price"), start, end);
    };
  }

  public static Specification<Product> getProductByCategoryAndBrand(
      final List<String> categoryList, final List<String> brandList) {
    return Specification.where(getProductByCategory(categoryList))
        .and(getProductByBrand(brandList));
  }

  public static Specification<Product> getProductByCategoryAndBrandAndPrice(
      final List<String> categoryList,
      final List<String> brandList,
      final float start,
      final float end) {
    return Specification.where(getProductByCategory(categoryList))
        .and(getProductByBrand(brandList))
        .and(getProductByPriceRange(start, end));
  }
}
