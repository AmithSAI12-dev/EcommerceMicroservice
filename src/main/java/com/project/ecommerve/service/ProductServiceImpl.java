package com.project.ecommerve.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.project.ecommerve.configuration.ProductSpecification;
import com.project.ecommerve.constants.ExceptionMessage;
import com.project.ecommerve.dto.ProductDto;
import com.project.ecommerve.dto.SearchDto;
import com.project.ecommerve.exception.NoProductAvailableException;
import com.project.ecommerve.exception.ProductDoesNotExistsException;
import com.project.ecommerve.model.Product;
import com.project.ecommerve.repository.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService {

  private final ProductRepository productRepository;
  private final String SUCCESS_MESSAGE = "Successfully Delete Product";

  @Autowired
  public ProductServiceImpl(ProductRepository productRepository) {
    this.productRepository = productRepository;
  }

  @Override
  public List<Product> retrieveAllProduct(SearchDto searchDto, Integer page, Integer size, String sortBy) throws NoProductAvailableException {
    List<Product> products;
    if (searchDto != null) {
      // If there is search condition then fetch all products with search condition and pagination
      products =
          productRepository
              .findAll(buildSpecification(searchDto), PageRequest.of(page, size, Sort.by(sortBy)))
              .getContent();
    } else {
      // Else fetch all products with pagination
      products = productRepository.findAll(PageRequest.of(0, 10)).getContent();
    }
    if (products.isEmpty()) {
      // If the list is empty then throwing exception
      throw new NoProductAvailableException(ExceptionMessage.NO_PRODUCT_AVAILABLE);
    }
    // Returning product list
    return products;
  }

  @Override
  public Product persistProduct(Product product) {
    // Persisting Product
    return productRepository.save(product);
  }

  @Override
  public Product updateProduct(Product product) throws ProductDoesNotExistsException {
    if (productRepository.findById(product.getId()).isEmpty()) {
      // If Product does not exists then throwing exception
      throw new ProductDoesNotExistsException(ExceptionMessage.PRODUCT_DOES_NOT_EXISTS);
    }
    // Returning Product
    return productRepository.save(product);
  }

  @Override
  public ProductDto deleteProduct(String productId) throws ProductDoesNotExistsException {
    Optional<Product> optionalProduct = productRepository.findById(productId);
    if (optionalProduct.isEmpty()) {
      // If Product does not exists then throwing Exception
      throw new ProductDoesNotExistsException(ExceptionMessage.PRODUCT_DOES_NOT_EXISTS);
    }
    // Deleting the product
    productRepository.deleteById(productId);
    // Returning Product Dto
    return new ProductDto(
        optionalProduct.get().getId(),
        optionalProduct.get().getName(),
        optionalProduct.get().getPrice(),
        optionalProduct.get().getImage1(),
        optionalProduct.get().getImage2(),
        optionalProduct.get().getImage3(),
        optionalProduct.get().getImage4(),
        optionalProduct.get().getDiscount(),
        optionalProduct.get().isAvailable(),
        optionalProduct.get().getDescription(),
        optionalProduct.get().getCreateDate(),
        SUCCESS_MESSAGE);
  }

  private Specification<Product> buildSpecification(SearchDto searchDto) {
    if (!searchDto.getCategories().isEmpty()
        && !searchDto.getBrands().isEmpty()
        && searchDto.getStart() != 0
        && searchDto.getEnd() != 0) {
      return ProductSpecification.getProductByCategoryAndBrandAndPrice(
          searchDto.getCategories(),
          searchDto.getBrands(),
          searchDto.getStart(),
          searchDto.getEnd());
    }
    if (!searchDto.getCategories().isEmpty() && !searchDto.getBrands().isEmpty()) {
      return ProductSpecification.getProductByCategoryAndBrand(
          searchDto.getCategories(), searchDto.getBrands());
    }
    if (!searchDto.getBrands().isEmpty()) {
      return ProductSpecification.getProductByBrand(searchDto.getBrands());
    }
    if (!searchDto.getCategories().isEmpty()) {
      return ProductSpecification.getProductByCategory(searchDto.getCategories());
    }
    return ProductSpecification.getProductByPriceRange(searchDto.getStart(), searchDto.getEnd());
  }
}
