package com.project.ecommerve.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.ecommerve.constants.ExceptionMessage;
import com.project.ecommerve.dto.TagDto;
import com.project.ecommerve.exception.NoTagsAvailableException;
import com.project.ecommerve.exception.TagAlreadyExistsException;
import com.project.ecommerve.exception.TagDoesNotExistsException;
import com.project.ecommerve.model.Tag;
import com.project.ecommerve.repository.TagRepository;

@Service
public class TagServiceImpl implements TagService {

  private final TagRepository tagRepository;
  private final String SUCCESS_MESSAGE = "Successfully deleted tag";

  @Autowired
  public TagServiceImpl(TagRepository tagRepository) {
    this.tagRepository = tagRepository;
  }

  @Override
  public List<Tag> retrieveAllTags() throws NoTagsAvailableException {
    List<Tag> tagList = tagRepository.findAll();
    if (tagList.isEmpty()) {
      throw new NoTagsAvailableException(ExceptionMessage.NO_TAG_AVAILABLE);
    }
    return tagList;
  }

  @Override
  public Tag persistTag(Tag tag) throws TagAlreadyExistsException {
    if (tagRepository.findById(tag.getName()).isPresent()) {
      throw new TagAlreadyExistsException(ExceptionMessage.TAG_ALREADY_EXISTS);
    }
    return tagRepository.save(tag);
  }

  @Override
  public Tag updateTag(Tag tag) throws TagDoesNotExistsException {
    if (tagRepository.findById(tag.getName()).isEmpty()) {
      throw new TagDoesNotExistsException(ExceptionMessage.TAG_DOES_NOT_EXISTS);
    }
    return tagRepository.save(tag);
  }

  @Override
  public TagDto deleteTag(String name) throws TagDoesNotExistsException {
    Optional<Tag> optionalTag = tagRepository.findById(name);
    if (optionalTag.isEmpty()) {
      throw new TagDoesNotExistsException(ExceptionMessage.TAG_DOES_NOT_EXISTS);
    }
    tagRepository.deleteById(name);
    return new TagDto(
        optionalTag.get().getName(), optionalTag.get().getDescription(), SUCCESS_MESSAGE);
  }
}
