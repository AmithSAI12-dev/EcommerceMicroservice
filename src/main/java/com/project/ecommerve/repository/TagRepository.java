package com.project.ecommerve.repository;

import com.project.ecommerve.model.Tag;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.jpa.repository.JpaRepository;

@ReadingConverter
public interface TagRepository extends JpaRepository<Tag, String> {
}
