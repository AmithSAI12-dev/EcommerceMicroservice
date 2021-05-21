package com.project.ecommerve.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.project.ecommerve.dto.BrandDto;
import com.project.ecommerve.exception.BrandDetailAlreadyExistsException;
import com.project.ecommerve.exception.BrandDetailDoesNotExistsException;
import com.project.ecommerve.exception.NoBrandsAvailableException;
import com.project.ecommerve.model.Brand;
import com.project.ecommerve.service.BrandService;

@RestController
@RequestMapping("/brand")
public class BrandController {

  @Autowired private final BrandService brandService;

  public BrandController(final BrandService brandService) {
    this.brandService = brandService;
  }

  @GetMapping(value = "/")
  public ResponseEntity<List<Brand>> getAllBrands(
      @RequestParam(defaultValue = "0", required = false) final Integer page,
      @RequestParam(defaultValue = "6", required = false) final Integer size,
      @RequestParam(defaultValue = "name", required = false) final String sortBy)
      throws NoBrandsAvailableException {
    final List<Brand> brands = brandService.retrieveAllBrands(page, size, sortBy);
    return new ResponseEntity<>(brands, HttpStatus.OK);
  }

  @PostMapping(value = "/add")
  public ResponseEntity<Brand> addBrand(@RequestBody final BrandDto brandDto)
      throws BrandDetailAlreadyExistsException {
    final Brand brand = brandService.persistBrandDetail(new Brand(brandDto));
    return new ResponseEntity<>(brand, HttpStatus.CREATED);
  }

  @PutMapping(value = "/update")
  public ResponseEntity<Brand> updateBrand(@RequestBody final BrandDto brandDto)
      throws BrandDetailDoesNotExistsException {
    final Brand brand = brandService.updateBrandDetail(new Brand(brandDto));
    return new ResponseEntity<>(brand, HttpStatus.CREATED);
  }

  @DeleteMapping(value = "/delete")
  public ResponseEntity<BrandDto> deleteBrand(@RequestParam final String brandName)
      throws BrandDetailDoesNotExistsException {
    final BrandDto brandDto = brandService.deleteBrandDetail(brandName);
    return new ResponseEntity<>(brandDto, HttpStatus.OK);
  }
}
