package com.project.ecommerve.service;

import com.project.ecommerve.dto.BrandDto;
import com.project.ecommerve.exception.BrandDetailAlreadyExistsException;
import com.project.ecommerve.exception.BrandDetailDoesNotExistsException;
import com.project.ecommerve.exception.NoBrandsAvailableException;
import com.project.ecommerve.model.Brand;

import java.util.List;

public interface BrandService {

    public List<Brand> retrieveAllBrands(Integer page, Integer size, String sortBy) throws NoBrandsAvailableException;
    public Brand addBrandDetail(Brand brand) throws BrandDetailAlreadyExistsException;
    public Brand updateBrandDetail(Brand brand) throws BrandDetailDoesNotExistsException;
    public BrandDto deleteBrandDetail(String name) throws BrandDetailDoesNotExistsException;
}
