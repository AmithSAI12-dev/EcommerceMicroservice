package com.project.ecommerve.controller;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collections;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.ecommerve.dto.ProductDto;
import com.project.ecommerve.dto.SearchDto;
import com.project.ecommerve.exception.NoProductAvailableException;
import com.project.ecommerve.exception.ProductDetailDoesNotExistsException;
import com.project.ecommerve.model.Product;
import com.project.ecommerve.service.ProductServiceImpl;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(controllers = ProductController.class)
class ProductControllerTest {

  @Autowired private MockMvc mockMvc;
  @MockBean private ProductServiceImpl productService;

  @Test
  void testRetrieveAllProduct_returnsStatusOK() throws Exception {
    when(productService.retrieveAllProduct(any(SearchDto.class), anyInt(), anyInt(), anyString()))
        .thenReturn(Collections.singletonList(new Product()));
    final String contentAsString =
        mockMvc
            .perform(
                get("/product/")
                    .param("page", "0")
                    .param("size", "10")
                    .param("sortBy", "Mock Name")
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .content(new ObjectMapper().writeValueAsString(new SearchDto())))
            .andExpect(status().isOk())
            .andReturn()
            .getResponse()
            .getContentAsString();
    assertNotNull(contentAsString, "Checking Not Null");
  }

  @Test
  void testRetrieveAllProduct_returnsBadRequest_whenExceptionIsThrown() throws Exception {
    when(productService.retrieveAllProduct(any(SearchDto.class), anyInt(), anyInt(), anyString()))
        .thenThrow(new NoProductAvailableException("Mock Exception"));
    final String contentAsString =
        mockMvc
            .perform(
                get("/product/")
                    .param("page", "0")
                    .param("size", "10")
                    .param("sortBy", "Mock Name")
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .content(new ObjectMapper().writeValueAsString(new SearchDto())))
            .andExpect(status().isNoContent())
            .andReturn()
            .getResponse()
            .getContentAsString();
    assertNotNull(contentAsString, "Checking Not Null");
  }

  @Test
  void testRetrieveProduct_returnsStatusOk() throws Exception {
    when(productService.retrieveProduct(anyString())).thenReturn(new Product());
    final String contentAsString =
        mockMvc
            .perform(get("/product/find").param("productId", "Mock ID"))
            .andExpect(status().isOk())
            .andReturn()
            .getResponse()
            .getContentAsString();
    assertNotNull(contentAsString, "Checking Not Null");
  }

  @Test
  void testRetrieveProduct_returnsStatusBadRequest_whenExceptionIsThrown() throws Exception {
    when(productService.retrieveProduct(anyString()))
        .thenThrow(new ProductDetailDoesNotExistsException("Mock Exception"));
    final String contentAsString =
        mockMvc
            .perform(get("/product/find").param("productId", "Mock ID"))
            .andExpect(status().isBadRequest())
            .andReturn()
            .getResponse()
            .getContentAsString();
    assertNotNull(contentAsString, "Checking Not Null");
  }

  @Test
  void testAddProduct_returnsStatusCreated() throws Exception {
    when(productService.persistProduct(any(Product.class))).thenReturn(new Product());
    final String contentAsString =
        mockMvc
            .perform(
                post("/product/add")
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .content(new ObjectMapper().writeValueAsString(new ProductDto())))
            .andExpect(status().isCreated())
            .andReturn()
            .getResponse()
            .getContentAsString();
    assertNotNull(contentAsString, "Checking Not Null");
  }

  @Test
  void testUpdateProduct_returnsStatusCreated() throws Exception {
    when(productService.updateProduct(any(Product.class))).thenReturn(new Product());
    final String contentAsString =
        mockMvc
            .perform(
                put("/product/update")
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .content(new ObjectMapper().writeValueAsString(new ProductDto())))
            .andExpect(status().isCreated())
            .andReturn()
            .getResponse()
            .getContentAsString();
    assertNotNull(contentAsString, "Checking Not Null");
  }

  @Test
  void testUpdateProduct_returnsStatusBadRequest_whenExceptionIsThrown() throws Exception {
    when(productService.updateProduct(any(Product.class)))
        .thenThrow(new ProductDetailDoesNotExistsException("Mock Exception"));
    final String contentAsString =
        mockMvc
            .perform(
                put("/product/update")
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .content(new ObjectMapper().writeValueAsString(new ProductDto())))
            .andExpect(status().isBadRequest())
            .andReturn()
            .getResponse()
            .getContentAsString();
    assertNotNull(contentAsString, "Checking Not Null");
  }

  @Test
  void testDeleteProduct_returnsStatusOK() throws Exception {
    when(productService.deleteProduct(anyString())).thenReturn(new ProductDto());
    final String contentAsString =
        mockMvc
            .perform(delete("/product/delete").param("productId", "Mock ID"))
            .andExpect(status().isOk())
            .andReturn()
            .getResponse()
            .getContentAsString();
    assertNotNull(contentAsString, "Checking Not Null");
  }

  @Test
  void testDeleteProduct_returnsStatusBadRequest_WhenExceptionIsThrown() throws Exception {
    when(productService.deleteProduct(anyString()))
        .thenThrow(new ProductDetailDoesNotExistsException("Mock Exception"));
    final String contentAsString =
        mockMvc
            .perform(delete("/product/delete").param("productId", "Mock ID"))
            .andExpect(status().isBadRequest())
            .andReturn()
            .getResponse()
            .getContentAsString();
    assertNotNull(contentAsString, "Checking Not Null");
  }
}
