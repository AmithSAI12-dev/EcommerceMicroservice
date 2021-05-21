package com.project.ecommerve.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
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

import com.project.ecommerve.dto.BrandDto;
import com.project.ecommerve.exception.BrandDetailAlreadyExistsException;
import com.project.ecommerve.exception.BrandDetailDoesNotExistsException;
import com.project.ecommerve.exception.NoBrandsAvailableException;
import com.project.ecommerve.model.Brand;
import com.project.ecommerve.repository.BrandRepository;

@ExtendWith(MockitoExtension.class)
class BrandServiceImplTest {

  @Mock private BrandRepository mockBrandRepository;
  @Mock private Page<Brand> mockPage;
  private static Brand mockBrand;
  @InjectMocks private BrandServiceImpl brandService;

  @BeforeAll
  static void setUp() {
    mockBrand = new Brand();
    mockBrand.setName("Mock Name");
  }

  @Test
  void testRetrieveAllBrands_returnsBrandList() throws NoBrandsAvailableException {
    when(mockBrandRepository.findAll(any(Pageable.class))).thenReturn(mockPage);
    when(mockPage.getContent()).thenReturn(Collections.singletonList(new Brand()));
    final List<Brand> brands = brandService.retrieveAllBrands(1, 1, "Mock Value");
    assertNotNull(brands, "Checking Not Null");
    verify(mockBrandRepository, atMostOnce()).findAll(any(Pageable.class));
  }

  @Test
  void testRetrieveAllBrands_throwsException_whenPageIsEmpty() {
    when(mockBrandRepository.findAll(any(Pageable.class))).thenReturn(Page.empty());
    assertThrows(
        NoBrandsAvailableException.class, () -> brandService.retrieveAllBrands(1, 1, "Mock Value"));
    verify(mockBrandRepository, atMostOnce()).findAll(any(Pageable.class));
  }

  @Test
  void testAddBrandDetail_returnsBrand() throws BrandDetailAlreadyExistsException {
    when(mockBrandRepository.findById(anyString())).thenReturn(Optional.empty());
    when(mockBrandRepository.save(any(Brand.class))).thenReturn(new Brand());
    final Brand brand = brandService.persistBrandDetail(mockBrand);
    assertNotNull(brand, "Checking Not Null");
    verify(mockBrandRepository, atMostOnce()).findById(anyString());
    verify(mockBrandRepository, atMostOnce()).save(any(Brand.class));
  }

  @Test
  void testAddBrandDetail_throwsException_whenBrandExists() {
    when(mockBrandRepository.findById(anyString())).thenReturn(Optional.of(new Brand()));
    assertThrows(
        BrandDetailAlreadyExistsException.class, () -> brandService.persistBrandDetail(mockBrand));
    verify(mockBrandRepository, atMostOnce()).findById(anyString());
  }

  @Test
  void testUpdateBrandDetail_returnsBrand() throws BrandDetailDoesNotExistsException {
    when(mockBrandRepository.findById(anyString())).thenReturn(Optional.of(mockBrand));
    when(mockBrandRepository.save(any(Brand.class))).thenReturn(mockBrand);
    final Brand brand = brandService.updateBrandDetail(mockBrand);
    assertNotNull(brand, "Checking Not Null");
    verify(mockBrandRepository, atMostOnce()).findById(anyString());
    verify(mockBrandRepository, atMostOnce()).save(any(Brand.class));
  }

  @Test
  void testUpdateBrandDetail_throwsException_whenBrandDoesNotExists() {
    when(mockBrandRepository.findById(anyString())).thenReturn(Optional.empty());
    assertThrows(
        BrandDetailDoesNotExistsException.class, () -> brandService.updateBrandDetail(mockBrand));
    verify(mockBrandRepository, atMostOnce()).findById(anyString());
  }

  @Test
  void testDeleteBrandDetail_returnsBrandDto() throws BrandDetailDoesNotExistsException {
    when(mockBrandRepository.findById(anyString())).thenReturn(Optional.of(mockBrand));
    doNothing().when(mockBrandRepository).deleteById(anyString());
    final BrandDto brandDto = brandService.deleteBrandDetail("Mock Name");
    assertNotNull(brandDto, "Checking Not Null");
    verify(mockBrandRepository, atMostOnce()).findById(anyString());
    verify(mockBrandRepository, atMostOnce()).deleteById(anyString());
  }

  @Test
  void testDeleteBrandDetail_throwsException_whenBrandDoesNotExists() {
    when(mockBrandRepository.findById(anyString())).thenReturn(Optional.empty());
    assertThrows(
        BrandDetailDoesNotExistsException.class, () -> brandService.deleteBrandDetail("Mock Name"));
    verify(mockBrandRepository, atMostOnce()).findById(anyString());
  }
}
