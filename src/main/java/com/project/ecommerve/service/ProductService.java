package com.project.ecommerve.service;

import java.util.List;

import com.project.ecommerve.dto.ProductDto;
import com.project.ecommerve.dto.SearchDto;
import com.project.ecommerve.exception.NoProductAvailableException;
import com.project.ecommerve.exception.ProductDetailDoesNotExistsException;
import com.project.ecommerve.model.Product;

public interface ProductService {

  public List<Product> retrieveAllProduct(
      SearchDto searchDto, Integer page, Integer size, String sortBY)
      throws NoProductAvailableException;

  public Product retrieveProduct(String productID) throws ProductDetailDoesNotExistsException;

  public Product persistProduct(Product product);

  public Product updateProduct(Product product) throws ProductDetailDoesNotExistsException;

  public ProductDto deleteProduct(String productId) throws ProductDetailDoesNotExistsException;
}
