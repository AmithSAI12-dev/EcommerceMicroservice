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
import com.project.ecommerve.dto.BrandDto;
import com.project.ecommerve.exception.BrandDetailAlreadyExistsException;
import com.project.ecommerve.exception.BrandDetailDoesNotExistsException;
import com.project.ecommerve.exception.NoBrandsAvailableException;
import com.project.ecommerve.model.Brand;
import com.project.ecommerve.service.BrandServiceImpl;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(controllers = BrandController.class)
class BrandControllerTest {

  @Autowired private MockMvc mockMvc;
  @MockBean private BrandServiceImpl brandServiceMock;

  @Test
  void testGetAllBrands_returnsStatusOK() throws Exception {
    when(brandServiceMock.retrieveAllBrands(anyInt(), anyInt(), anyString()))
        .thenReturn(Collections.singletonList(new Brand()));
    final String contentAsString =
        mockMvc
            .perform(get("/brand/"))
            .andExpect(status().isOk())
            .andReturn()
            .getResponse()
            .getContentAsString();
    assertNotNull(contentAsString, "Checking Not Null");
  }

  @Test
  void testGetAllBrands_returnsStatusBadRequest_whenExceptionISThrown() throws Exception {
    when(brandServiceMock.retrieveAllBrands(anyInt(), anyInt(), anyString()))
        .thenThrow(new NoBrandsAvailableException("Mock Exception"));
    final String contentAsString =
        mockMvc
            .perform(get("/brand/"))
            .andExpect(status().isNoContent())
            .andReturn()
            .getResponse()
            .getContentAsString();
    assertNotNull(contentAsString, "Checking Not Null");
  }

  @Test
  void testAddBrand_returnsStatusCreated() throws Exception {
    when(brandServiceMock.persistBrandDetail(any(Brand.class))).thenReturn(new Brand());
    final String contentAsString =
        mockMvc
            .perform(
                post("/brand/add")
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .content(new ObjectMapper().writeValueAsString(new Brand())))
            .andExpect(status().isCreated())
            .andReturn()
            .getResponse()
            .getContentAsString();
    assertNotNull(contentAsString, "Checking Not Null");
  }

  @Test
  void testAddBrand_returnsStatusBadRequest_whenExceptionIsThrown() throws Exception {
    when(brandServiceMock.persistBrandDetail(any(Brand.class)))
        .thenThrow(new BrandDetailAlreadyExistsException("Mock Exception"));
    final String contentAsString =
        mockMvc
            .perform(
                post("/brand/add")
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .content(new ObjectMapper().writeValueAsString(new Brand())))
            .andExpect(status().isBadRequest())
            .andReturn()
            .getResponse()
            .getContentAsString();
    assertNotNull(contentAsString, "Checking Not Null");
  }

  @Test
  void testUpdateBrand_returnsStatusCreated() throws Exception {
    when(brandServiceMock.updateBrandDetail(any(Brand.class))).thenReturn(new Brand());
    final String contentAsString =
        mockMvc
            .perform(
                put("/brand/update")
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .content(new ObjectMapper().writeValueAsString(new Brand())))
            .andExpect(status().isCreated())
            .andReturn()
            .getResponse()
            .getContentAsString();
    assertNotNull(contentAsString, "Checking Not Null");
  }

  @Test
  void testUpdateBrand_returnsStatusBadRequest_whenExceptionIsThrown() throws Exception {
    when(brandServiceMock.updateBrandDetail(any(Brand.class)))
        .thenThrow(new BrandDetailDoesNotExistsException("Mock Exception"));
    final String contentAsString =
        mockMvc
            .perform(
                put("/brand/update")
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .content(new ObjectMapper().writeValueAsString(new Brand())))
            .andExpect(status().isBadRequest())
            .andReturn()
            .getResponse()
            .getContentAsString();
    assertNotNull(contentAsString, "Checking Not Null");
  }

  @Test
  void testDeleteBrand_returnsStatusOK() throws Exception {
    when(brandServiceMock.deleteBrandDetail(anyString())).thenReturn(new BrandDto());
    final String contentAsString =
        mockMvc
            .perform(delete("/brand/delete").param("brandName", "Mock Brand Name"))
            .andExpect(status().isOk())
            .andReturn()
            .getResponse()
            .getContentAsString();
    assertNotNull(contentAsString, "Checking Not Null");
  }

  @Test
  void testDeleteBrand_returnsStatusBadRequest_whenExceptionIsThrown() throws Exception {
    when(brandServiceMock.deleteBrandDetail(anyString()))
        .thenThrow(new BrandDetailDoesNotExistsException("Mock Exception"));
    final String contentAsString =
        mockMvc
            .perform(delete("/brand/delete").param("brandName", "Mock Brand Name"))
            .andExpect(status().isBadRequest())
            .andReturn()
            .getResponse()
            .getContentAsString();
    assertNotNull(contentAsString, "Checking Not Null");
  }
}
