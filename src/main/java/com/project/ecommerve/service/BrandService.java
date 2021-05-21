package com.project.ecommerve.service;

import java.util.List;

import com.project.ecommerve.dto.BrandDto;
import com.project.ecommerve.exception.BrandDetailAlreadyExistsException;
import com.project.ecommerve.exception.BrandDetailDoesNotExistsException;
import com.project.ecommerve.exception.NoBrandsAvailableException;
import com.project.ecommerve.model.Brand;

public interface BrandService {

  List<Brand> retrieveAllBrands(Integer page, Integer size, String sortBy)
      throws NoBrandsAvailableException;

  Brand persistBrandDetail(Brand brand) throws BrandDetailAlreadyExistsException;

  Brand updateBrandDetail(Brand brand) throws BrandDetailDoesNotExistsException;

  BrandDto deleteBrandDetail(String name) throws BrandDetailDoesNotExistsException;
}
