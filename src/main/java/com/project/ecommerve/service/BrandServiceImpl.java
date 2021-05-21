package com.project.ecommerve.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.project.ecommerve.constants.ExceptionMessage;
import com.project.ecommerve.dto.BrandDto;
import com.project.ecommerve.exception.BrandDetailAlreadyExistsException;
import com.project.ecommerve.exception.BrandDetailDoesNotExistsException;
import com.project.ecommerve.exception.NoBrandsAvailableException;
import com.project.ecommerve.model.Brand;
import com.project.ecommerve.repository.BrandRepository;

@Service
public class BrandServiceImpl implements BrandService {

  private final BrandRepository brandRepository;
  private final String SUCCESSFULLY_DELETED_BRAND = "Successfully Deleted Brand Detail";

  @Autowired
  public BrandServiceImpl(final BrandRepository brandRepository) {
    this.brandRepository = brandRepository;
  }

  @Override
  public List<Brand> retrieveAllBrands(final Integer page, final Integer size, final String sortBy)
      throws NoBrandsAvailableException {
    // Fetching brands from the database, Note: Pagination and sorting is used
    final Page<Brand> brandPage =
        brandRepository.findAll(PageRequest.of(page, size, Sort.by(sortBy)));
    if (brandPage.getContent().isEmpty()) {
      // If the page content is empty then throwing exception
      throw new NoBrandsAvailableException(ExceptionMessage.NO_BRAND_AVAILABLE);
    }
    // Returning Page Content
    return brandPage.getContent();
  }

  @Override
  public Brand persistBrandDetail(final Brand brand) throws BrandDetailAlreadyExistsException {
    if (brandRepository.findById(brand.getName()).isPresent()) {
      // If Brand exists in the database then throwing exception
      throw new BrandDetailAlreadyExistsException(ExceptionMessage.BRAND_ALREADY_EXISTS);
    }
    // Returning Brand after persisting into the database
    return brandRepository.save(brand);
  }

  @Override
  public Brand updateBrandDetail(final Brand brand) throws BrandDetailDoesNotExistsException {
    if (brandRepository.findById(brand.getName()).isEmpty()) {
      throw new BrandDetailDoesNotExistsException(ExceptionMessage.BRAND_DOES_NOT_EXISTS);
    }
    return brandRepository.save(brand);
  }

  @Override
  public BrandDto deleteBrandDetail(final String name) throws BrandDetailDoesNotExistsException {
    final Optional<Brand> optionalBrand = brandRepository.findById(name);
    if (optionalBrand.isEmpty()) {
      // If Brand does not exists then throwing exception
      throw new BrandDetailDoesNotExistsException(ExceptionMessage.BRAND_DOES_NOT_EXISTS);
    }
    // Deleting brand detail
    brandRepository.deleteById(name);
    return new BrandDto(
        optionalBrand.get().getName(),
        optionalBrand.get().getDescription(),
        optionalBrand.get().getImageUrl(),
        SUCCESSFULLY_DELETED_BRAND);
  }
}
