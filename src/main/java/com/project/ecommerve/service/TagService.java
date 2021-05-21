package com.project.ecommerve.service;

import java.util.List;

import com.project.ecommerve.dto.TagDto;
import com.project.ecommerve.exception.NoTagsAvailableException;
import com.project.ecommerve.exception.TagAlreadyExistsException;
import com.project.ecommerve.exception.TagDoesNotExistsException;
import com.project.ecommerve.model.Tag;

public interface TagService {

  List<Tag> retrieveAllTags() throws NoTagsAvailableException;

  Tag persistTag(Tag tag) throws TagAlreadyExistsException;

  Tag updateTag(Tag tag) throws TagDoesNotExistsException;

  TagDto deleteTag(String name) throws TagDoesNotExistsException;
}
