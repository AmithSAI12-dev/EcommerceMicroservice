package com.project.ecommerve.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
import com.project.ecommerve.exception.ProductDetailDoesNotExistsException;
import com.project.ecommerve.model.Product;
import com.project.ecommerve.repository.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService {

  private final ProductRepository productRepository;
  private final String SUCCESS_MESSAGE = "Successfully Delete Product";

  @Autowired
  public ProductServiceImpl(final ProductRepository productRepository) {
    this.productRepository = productRepository;
  }

  @Override
  public List<Product> retrieveAllProduct(
      final SearchDto searchDto, final Integer page, final Integer size, final String sortBy)
      throws NoProductAvailableException {
    List<Product> products;
    if (searchDto != null) {
      // If there is search condition then fetch all products
      // with search condition and pagination
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
  public Product retrieveProduct(final String productID)
      throws ProductDetailDoesNotExistsException {
    final Optional<Product> optionalProduct = productRepository.findById(productID);
    if (optionalProduct.isEmpty()) {
      // If the product does not exists then throwing exception
      throw new ProductDetailDoesNotExistsException(ExceptionMessage.PRODUCT_DOES_NOT_EXISTS);
    }
    // Returning product
    return optionalProduct.get();
  }

  @Override
  public Product persistProduct(final Product product) {
    // Persisting Product
    return productRepository.save(product);
  }

  @Override
  public Product updateProduct(final Product product) throws ProductDetailDoesNotExistsException {
    if (productRepository.findById(product.getId()).isEmpty()) {
      // If Product does not exists then throwing exception
      throw new ProductDetailDoesNotExistsException(ExceptionMessage.PRODUCT_DOES_NOT_EXISTS);
    }
    // Returning Product
    return productRepository.save(product);
  }

  @Override
  public ProductDto deleteProduct(final String productId)
      throws ProductDetailDoesNotExistsException {
    final Optional<Product> optionalProduct = productRepository.findById(productId);
    if (optionalProduct.isEmpty()) {
      // If Product does not exists then throwing Exception
      throw new ProductDetailDoesNotExistsException(ExceptionMessage.PRODUCT_DOES_NOT_EXISTS);
    }
    // Deleting the product
    productRepository.deleteById(productId);
    // Returning Product Dto
    return new ProductDto(
        optionalProduct.get().getId(),
        optionalProduct.get().getName(),
        optionalProduct.get().getPrice(),
        optionalProduct.get().getImages() != null
            ? optionalProduct.get().getImages().stream().collect(Collectors.toList())
            : null,
        optionalProduct.get().getSize() != null
            ? optionalProduct.get().getSize().stream().collect(Collectors.toList())
            : null,
        optionalProduct.get().getTags() != null
            ? optionalProduct.get().getTags().stream().collect(Collectors.toList())
            : null,
        optionalProduct.get().getDiscount(),
        optionalProduct.get().isAvailable(),
        optionalProduct.get().getDescription(),
        optionalProduct.get().getCreateDate(),
        null,
        null,
        SUCCESS_MESSAGE);
  }

  private Specification<Product> buildSpecification(final SearchDto searchDto) {
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
