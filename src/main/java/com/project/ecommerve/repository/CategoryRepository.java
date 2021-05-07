package com.project.ecommerve.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.ecommerve.model.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, String> {}
