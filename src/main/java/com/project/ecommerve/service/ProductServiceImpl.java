package com.project.ecommerve.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.ecommerve.constants.ExceptionMessage;
import com.project.ecommerve.dto.ProductDto;
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
}
