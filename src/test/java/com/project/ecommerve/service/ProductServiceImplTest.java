package com.project.ecommerve.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import com.project.ecommerve.dto.ProductDto;
import com.project.ecommerve.dto.SearchDto;
import com.project.ecommerve.exception.NoProductAvailableException;
import com.project.ecommerve.exception.ProductDetailDoesNotExistsException;
import com.project.ecommerve.model.Brand;
import com.project.ecommerve.model.Category;
import com.project.ecommerve.model.Product;
import com.project.ecommerve.repository.ProductRepository;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

  @Mock private ProductRepository productRepositoryMock;
  @Mock private Page<Product> productPageMock;
  private static Product productMock;
  @InjectMocks private ProductServiceImpl productService;

  @BeforeAll
  static void setUp() {
    productMock = new Product();
    productMock.setId("Mock Product ID");
    productMock.setCategory(new Category("Mock Name"));
    productMock.setBrand(new Brand("Mock Name"));
  }

  @Test
  void testRetrieveAllProduct_returnsProductList_withCategorySearchFilter()
      throws NoProductAvailableException {
    final SearchDto searchDto = new SearchDto();
    searchDto.setCategories(Collections.singletonList("Mock category"));
    when(productRepositoryMock.findAll(any(Specification.class), any(Pageable.class)))
        .thenReturn(productPageMock);
    when(productPageMock.getContent()).thenReturn(Collections.singletonList(productMock));
    final List<Product> productList =
        productService.retrieveAllProduct(searchDto, 0, 10, "Mock Sort");
    assertNotNull(productList, "Checking Not Null");
    verify(productRepositoryMock, atMostOnce())
        .findAll(any(Specification.class), any(Pageable.class));
    verify(productPageMock, atMostOnce()).getContent();
  }

  @Test
  void testRetrieveAllProduct_returnsProductList_withBrandSearchFilter()
      throws NoProductAvailableException {
    final SearchDto searchDto = new SearchDto();
    searchDto.setBrands(Collections.singletonList("Mock brand"));
    when(productRepositoryMock.findAll(any(Specification.class), any(Pageable.class)))
        .thenReturn(productPageMock);
    when(productPageMock.getContent()).thenReturn(Collections.singletonList(productMock));
    final List<Product> productList =
        productService.retrieveAllProduct(searchDto, 0, 10, "Mock Sort");
    assertNotNull(productList, "Checking Not Null");
    verify(productRepositoryMock, atMostOnce())
        .findAll(any(Specification.class), any(Pageable.class));
    verify(productPageMock, atMostOnce()).getContent();
  }

  @Test
  void testRetrieveAllProduct_returnsProductList_withPriceSearchFilter()
      throws NoProductAvailableException {
    final SearchDto searchDto = new SearchDto();
    searchDto.setStart(10);
    searchDto.setEnd(10);
    when(productRepositoryMock.findAll(any(Specification.class), any(Pageable.class)))
        .thenReturn(productPageMock);
    when(productPageMock.getContent()).thenReturn(Collections.singletonList(productMock));
    final List<Product> productList =
        productService.retrieveAllProduct(searchDto, 0, 10, "Mock Sort");
    assertNotNull(productList, "Checking Not Null");
    verify(productRepositoryMock, atMostOnce())
        .findAll(any(Specification.class), any(Pageable.class));
    verify(productPageMock, atMostOnce()).getContent();
  }

  @Test
  void testRetrieveAllProduct_returnsProductList_withBranAndCategorySearchFilter()
      throws NoProductAvailableException {
    final SearchDto searchDto = new SearchDto();
    searchDto.setCategories(Collections.singletonList("Mock Category"));
    searchDto.setBrands(Collections.singletonList("Mock Brand"));
    when(productRepositoryMock.findAll(any(Specification.class), any(Pageable.class)))
        .thenReturn(productPageMock);
    when(productPageMock.getContent()).thenReturn(Collections.singletonList(productMock));
    final List<Product> productList =
        productService.retrieveAllProduct(searchDto, 0, 10, "Mock Sort");
    assertNotNull(productList, "Checking Not Null");
    verify(productRepositoryMock, atMostOnce())
        .findAll(any(Specification.class), any(Pageable.class));
    verify(productPageMock, atMostOnce()).getContent();
  }

  @Test
  void testRetrieveAllProduct_returnsProductList_withAllSearchFilter()
      throws NoProductAvailableException {
    final SearchDto searchDto = new SearchDto();
    searchDto.setCategories(Collections.singletonList("Mock Category"));
    searchDto.setBrands(Collections.singletonList("Mock Brand"));
    searchDto.setStart(10);
    searchDto.setEnd(10);
    when(productRepositoryMock.findAll(any(Specification.class), any(Pageable.class)))
        .thenReturn(productPageMock);
    when(productPageMock.getContent()).thenReturn(Collections.singletonList(productMock));
    final List<Product> productList =
        productService.retrieveAllProduct(searchDto, 0, 10, "Mock Sort");
    assertNotNull(productList, "Checking Not Null");
    verify(productRepositoryMock, atMostOnce())
        .findAll(any(Specification.class), any(Pageable.class));
    verify(productPageMock, atMostOnce()).getContent();
  }

  @Test
  void testRetrieveAllProduct_returnsProductList_withOutSearchFilter()
      throws NoProductAvailableException {
    when(productRepositoryMock.findAll(any(Pageable.class))).thenReturn(productPageMock);
    when(productPageMock.getContent()).thenReturn(Collections.singletonList(productMock));
    final List<Product> productList = productService.retrieveAllProduct(null, 0, 10, "Mock Sort");
    assertNotNull(productList, "Checking Not Null");
    verify(productRepositoryMock, atMostOnce()).findAll(any(Pageable.class));
    verify(productPageMock, atMostOnce()).getContent();
  }

  @Test
  void testRetrieveAllProduct_throwsException_whenPageIsEmpty() {
    when(productRepositoryMock.findAll(any(Pageable.class))).thenReturn(Page.empty());
    assertThrows(
        NoProductAvailableException.class,
        () -> productService.retrieveAllProduct(null, 0, 10, "mock"));
    verify(productRepositoryMock, atMostOnce()).findAll(any(Pageable.class));
  }

  @Test
  void testRetrieveProduct_returnsProduct() throws ProductDetailDoesNotExistsException {
    when(productRepositoryMock.findById(anyString())).thenReturn(Optional.of(productMock));
    final Product product = productService.retrieveProduct("Mock ID");
    assertNotNull(product, "Checking Not Null");
    verify(productRepositoryMock, atMostOnce()).findById(anyString());
  }

  @Test
  void testRetrieveProduct_throwsException_whenProductDoesNotExists() {
    when(productRepositoryMock.findById(anyString())).thenReturn(Optional.empty());
    assertThrows(
        ProductDetailDoesNotExistsException.class, () -> productService.retrieveProduct("Mock ID"));
    verify(productRepositoryMock, atMostOnce()).findById(anyString());
  }

  @Test
  void testPersistProduct_returnsProduct() {
    when(productRepositoryMock.save(any(Product.class))).thenReturn(productMock);
    final Product product = productService.persistProduct(productMock);
    assertNotNull(product, "Checking Not Null");
    verify(productRepositoryMock, atMostOnce()).save(any(Product.class));
  }

  @Test
  void testUpdateProduct_returnsProduct() throws ProductDetailDoesNotExistsException {
    when(productRepositoryMock.findById(anyString())).thenReturn(Optional.of(productMock));
    when(productRepositoryMock.save(any(Product.class))).thenReturn(productMock);
    final Product product = productService.updateProduct(productMock);
    assertNotNull(product, "Checking Not Null");
    verify(productRepositoryMock, atMostOnce()).findById(anyString());
    verify(productRepositoryMock, atMostOnce()).save(any(Product.class));
  }

  @Test
  void testUpdateProduct_throwsException_whenProductDoesNotExists() {
    when(productRepositoryMock.findById(anyString())).thenReturn(Optional.empty());
    assertThrows(
        ProductDetailDoesNotExistsException.class, () -> productService.updateProduct(productMock));
    verify(productRepositoryMock, atMostOnce()).findById(anyString());
  }

  @Test
  void testDeleteProduct_returnsProductDto() throws ProductDetailDoesNotExistsException {
    when(productRepositoryMock.findById(anyString())).thenReturn(Optional.of(productMock));
    doNothing().when(productRepositoryMock).deleteById(anyString());
    final ProductDto productDto = productService.deleteProduct("Mock Product ID");
    assertNotNull(productDto, "Checking Not Null");
    verify(productRepositoryMock, atMostOnce()).findById(anyString());
    verify(productRepositoryMock, atMostOnce()).deleteById(anyString());
  }

  @Test
  void testDeleteProduct_throwsException_whenProductDoesNotExists() {
    when(productRepositoryMock.findById(anyString())).thenReturn(Optional.empty());
    assertThrows(
        ProductDetailDoesNotExistsException.class,
        () -> productService.deleteProduct("Mock Product ID"));
    verify(productRepositoryMock, atMostOnce()).findById(anyString());
  }
}
