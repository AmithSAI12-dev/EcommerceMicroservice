package com.project.ecommerve.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.project.ecommerve.dto.SearchDto;
import com.project.ecommerve.exception.NoProductAvailableException;
import com.project.ecommerve.model.Product;
import com.project.ecommerve.service.ProductService;

@RestController
@RequestMapping(value = "/product")
public class ProductController {

  private final ProductService productService;

  @Autowired
  public ProductController(ProductService productService) {
    this.productService = productService;
  }

  @GetMapping(value = "/")
  public ResponseEntity<List<Product>> getAllProducts(
      @RequestBody(required = false) SearchDto searchDto) throws NoProductAvailableException {
    List<Product> products = productService.retrieveAllProduct(searchDto);
    return new ResponseEntity<>(products, HttpStatus.OK);
  }
}
