package com.project.ecommerve.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.project.ecommerve.dto.ProductDto;
import com.project.ecommerve.exception.ProductDoesNotExistsException;
import com.project.ecommerve.model.Product;
import com.project.ecommerve.repository.ProductRepository;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

  @Mock private ProductRepository productRepositoryMock;
  private static Product productMock;
  @InjectMocks private ProductServiceImpl productService;

  @BeforeAll
  static void setUp() {
    productMock = new Product();
    productMock.setId("Mock Product ID");
  }

  @Test
  void testPersistProduct_returnsProduct() {
    when(productRepositoryMock.save(any(Product.class))).thenReturn(productMock);
    Product product = productService.persistProduct(productMock);
    assertNotNull(product);
    verify(productRepositoryMock, atMostOnce()).save(any(Product.class));
  }

  @Test
  void testUpdateProduct_returnsProduct() throws ProductDoesNotExistsException {
    when(productRepositoryMock.findById(anyString())).thenReturn(Optional.of(productMock));
    when(productRepositoryMock.save(any(Product.class))).thenReturn(productMock);
    Product product = productService.updateProduct(productMock);
    assertNotNull(product);
    verify(productRepositoryMock, atMostOnce()).findById(anyString());
    verify(productRepositoryMock, atMostOnce()).save(any(Product.class));
  }

  @Test
  void testUpdateProduct_throwsException_whenProductDoesNotExists() {
    when(productRepositoryMock.findById(anyString())).thenReturn(Optional.empty());
    assertThrows(
        ProductDoesNotExistsException.class, () -> productService.updateProduct(productMock));
    verify(productRepositoryMock, atMostOnce()).findById(anyString());
  }

  @Test
  void testDeleteProduct_returnsProductDto() throws ProductDoesNotExistsException {
    when(productRepositoryMock.findById(anyString())).thenReturn(Optional.of(productMock));
    doNothing().when(productRepositoryMock).deleteById(anyString());
    ProductDto productDto = productService.deleteProduct("Mock Product ID");
    verify(productRepositoryMock, atMostOnce()).findById(anyString());
    verify(productRepositoryMock, atMostOnce()).deleteById(anyString());
  }

  @Test
  void testDeleteProduct_throwsException_whenProductDoesNotExists() {
    when(productRepositoryMock.findById(anyString())).thenReturn(Optional.empty());
    assertThrows(
        ProductDoesNotExistsException.class, () -> productService.deleteProduct("Mock Product ID"));
    verify(productRepositoryMock, atMostOnce()).findById(anyString());
  }
}
