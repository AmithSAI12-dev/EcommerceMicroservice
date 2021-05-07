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
@RequestMapping(value = "/brand")
public class BrandController {

  @Autowired private final BrandService brandService;

  public BrandController(BrandService brandService) {
    this.brandService = brandService;
  }

  @GetMapping(value = "/")
  public ResponseEntity<List<Brand>> getAllBrands(
      @RequestParam(defaultValue = "0", required = false) Integer page,
      @RequestParam(defaultValue = "6", required = false) Integer size,
      @RequestParam(defaultValue = "name", required = false) String sortBy)
      throws NoBrandsAvailableException {
    List<Brand> brands = brandService.retrieveAllBrands(page, size, sortBy);
    return new ResponseEntity<>(brands, HttpStatus.OK);
  }

  @PostMapping(value = "/add")
  public ResponseEntity<Brand> addBrand(@RequestBody BrandDto brandDto)
      throws BrandDetailAlreadyExistsException {
    Brand brand = brandService.persistBrandDetail(new Brand(brandDto));
    return new ResponseEntity<>(brand, HttpStatus.CREATED);
  }

  @PutMapping(value = "/update")
  public ResponseEntity<Brand> updateBrand(@RequestBody BrandDto brandDto)
      throws BrandDetailDoesNotExistsException {
    Brand brand = brandService.updateBrandDetail(new Brand(brandDto));
    return new ResponseEntity<>(brand, HttpStatus.CREATED);
  }

  @DeleteMapping(value = "/delete")
  public ResponseEntity<BrandDto> deleteBrand(@RequestParam String brandName)
      throws BrandDetailDoesNotExistsException {
    BrandDto brandDto = brandService.deleteBrandDetail(brandName);
    return new ResponseEntity<>(brandDto, HttpStatus.OK);
  }
}
