package com.project.ecommerve.service;

import java.util.List;

import com.project.ecommerve.dto.TagDto;
import com.project.ecommerve.exception.NoTagsAvailableException;
import com.project.ecommerve.exception.TagAlreadyExistsException;
import com.project.ecommerve.exception.TagDoesNotExistsException;
import com.project.ecommerve.model.Tag;

public interface TagService {

  public List<Tag> retrieveAllTags() throws NoTagsAvailableException;

  public Tag persistTag(Tag tag) throws TagAlreadyExistsException;

  public Tag updateTag(Tag tag) throws TagDoesNotExistsException;

  public TagDto deleteTag(String name) throws TagDoesNotExistsException;
}
