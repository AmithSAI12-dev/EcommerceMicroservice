package com.project.ecommerve.service;

import com.project.ecommerve.dto.ProductDto;
import com.project.ecommerve.exception.ProductDoesNotExistsException;
import com.project.ecommerve.model.Product;

public interface ProductService {

  public Product persistProduct(Product product);

  public Product updateProduct(Product product) throws ProductDoesNotExistsException;

  public ProductDto deleteProduct(String productId) throws ProductDoesNotExistsException;
}
