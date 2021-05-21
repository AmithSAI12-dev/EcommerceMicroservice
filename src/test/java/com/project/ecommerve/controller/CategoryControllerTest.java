package com.project.ecommerve.controller;

import static org.junit.jupiter.api.Assertions.*;
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
import com.project.ecommerve.dto.CategoryDto;
import com.project.ecommerve.exception.CategoryDetailAlreadyExistsException;
import com.project.ecommerve.exception.CategoryDetailDoesNotExistsException;
import com.project.ecommerve.exception.NoCategoryAvailableException;
import com.project.ecommerve.model.Category;
import com.project.ecommerve.service.CategoryServiceImpl;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(controllers = CategoryController.class)
class CategoryControllerTest {

  @MockBean private CategoryServiceImpl categoryService;
  @Autowired private MockMvc mockMvc;

  @Test
  void testGetAllCategories_returnsStatusOk() throws Exception {
    when(categoryService.retrieveAllCategories(anyInt(), anyInt(), anyString()))
        .thenReturn(Collections.singletonList(new Category()));
    final String contentAsString =
        mockMvc
            .perform(
                get("/category/")
                    .param("page", "0")
                    .param("size", "10")
                    .param("sortBy", "Mock Name"))
            .andExpect(status().isOk())
            .andReturn()
            .getResponse()
            .getContentAsString();
    assertNotNull(contentAsString, "Checking Not Null");
  }

  @Test
  void testGetAllCategories_returnsStatusBadRequest_whenExceptionIsThrown() throws Exception {
    when(categoryService.retrieveAllCategories(anyInt(), anyInt(), anyString()))
        .thenThrow(new NoCategoryAvailableException("Mock Exception"));
    final String contentAsString =
        mockMvc
            .perform(
                get("/category/")
                    .param("page", "0")
                    .param("size", "10")
                    .param("sortBy", "Mock Name"))
            .andExpect(status().isNoContent())
            .andReturn()
            .getResponse()
            .getContentAsString();
    assertNotNull(contentAsString, "Checking Not Null");
  }

  @Test
  void testAddCategory_returnsStatusCreated() throws Exception {
    when(categoryService.persistCategory(any(Category.class))).thenReturn(new Category());
    final String contentAsString =
        mockMvc
            .perform(
                post("/category/add")
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .content(new ObjectMapper().writeValueAsString(new Category())))
            .andExpect(status().isCreated())
            .andReturn()
            .getResponse()
            .getContentAsString();
    assertNotNull(contentAsString, "Checking Not Null");
  }

  @Test
  void testAddCategory_returnsStatusBadRequest_whenExceptionIsThrown() throws Exception {
    when(categoryService.persistCategory(any(Category.class)))
        .thenThrow(new CategoryDetailAlreadyExistsException("Mock Exception"));
    final String contentAsString =
        mockMvc
            .perform(
                post("/category/add")
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .content(new ObjectMapper().writeValueAsString(new Category())))
            .andExpect(status().isBadRequest())
            .andReturn()
            .getResponse()
            .getContentAsString();
    assertNotNull(contentAsString, "Checking Not Null");
  }

  @Test
  void testUpdateCategory_returnsStatusCreated() throws Exception {
    when(categoryService.updateCategory(any(Category.class))).thenReturn(new Category());
    final String contentAsString =
        mockMvc
            .perform(
                put("/category/update")
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .content(new ObjectMapper().writeValueAsString(new Category())))
            .andExpect(status().isCreated())
            .andReturn()
            .getResponse()
            .getContentAsString();
    assertNotNull(contentAsString, "Checking Not Null");
  }

  @Test
  void testUpdateCategory_returnsStatusBadRequest_whenExceptionIsThrown() throws Exception {
    when(categoryService.updateCategory(any(Category.class)))
        .thenThrow(new CategoryDetailDoesNotExistsException("Mock Exception"));
    final String contentAsString =
        mockMvc
            .perform(
                put("/category/update")
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .content(new ObjectMapper().writeValueAsString(new Category())))
            .andExpect(status().isBadRequest())
            .andReturn()
            .getResponse()
            .getContentAsString();
    assertNotNull(contentAsString, "Checking Not Null");
  }

  @Test
  void testDeleteCategory_returnsStatusOk() throws Exception {
    when(categoryService.deleteCategory(anyString())).thenReturn(new CategoryDto());
    final String contentAsString =
        mockMvc
            .perform(delete("/category/delete").param("name", "Mock Name"))
            .andExpect(status().isOk())
            .andReturn()
            .getResponse()
            .getContentAsString();
    assertNotNull(contentAsString, "Checking Not Null");
  }

  @Test
  void testDeleteCategory_returnsStatusBadRequest_whenExceptionIsThrown() throws Exception {
    when(categoryService.deleteCategory(anyString()))
        .thenThrow(new CategoryDetailDoesNotExistsException("Mock Exception"));
    final String contentAsString =
        mockMvc
            .perform(delete("/category/delete").param("name", "Mock Name"))
            .andExpect(status().isBadRequest())
            .andReturn()
            .getResponse()
            .getContentAsString();
    assertNotNull(contentAsString, "Checking Not Null");
  }
}
