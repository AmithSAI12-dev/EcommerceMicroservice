package com.project.ecommerve.controller;

import java.util.List;

import com.project.ecommerve.dto.ProductDto;
import com.project.ecommerve.exception.ProductDoesNotExistsException;
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
      @RequestBody(required = false) SearchDto searchDto,
      @RequestParam(defaultValue = "0", required = false) Integer page,
      @RequestParam(defaultValue = "10", required = false) Integer size,
      @RequestParam(defaultValue = "name", required = false) String sortBy)
      throws NoProductAvailableException {
    List<Product> products = productService.retrieveAllProduct(searchDto, page, size, sortBy);
    return new ResponseEntity<>(products, HttpStatus.OK);
  }

  @GetMapping(value = "/find")
  public ResponseEntity<Product> getProduct(@RequestParam String productId) throws ProductDoesNotExistsException {
    Product product = productService.retrieveProduct(productId);
    return new ResponseEntity<>(product, HttpStatus.OK);
  }

  @PostMapping(value = "/add")
  public ResponseEntity<Product> addProduct(@RequestBody ProductDto productDto) {
    Product product = productService.persistProduct(new Product(productDto));
    return new ResponseEntity<>(product, HttpStatus.CREATED);
  }

  @PutMapping(value = "/update")
  public ResponseEntity<Product> updateProduct(@RequestBody ProductDto productDto) throws ProductDoesNotExistsException {
    Product product = productService.updateProduct(new Product(productDto));
    return new ResponseEntity<>(product, HttpStatus.CREATED);
  }

  @DeleteMapping(value = "/delete")
  public ResponseEntity<ProductDto> deleteProduct(@RequestParam String productId) throws ProductDoesNotExistsException {
    ProductDto productDto = productService.deleteProduct(productId);
    return new ResponseEntity<>(productDto, HttpStatus.OK);
  }
}
