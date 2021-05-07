package com.project.ecommerve.repository;

import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.jpa.repository.JpaRepository;

import com.project.ecommerve.model.Tag;

@ReadingConverter
public interface TagRepository extends JpaRepository<Tag, String> {}
