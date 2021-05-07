package com.project.ecommerve.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.ecommerve.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {}
