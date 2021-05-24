package com.project.ecommerve.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.project.ecommerve.dto.TagDto;
import com.project.ecommerve.exception.NoTagsAvailableException;
import com.project.ecommerve.exception.TagAlreadyExistsException;
import com.project.ecommerve.exception.TagDoesNotExistsException;
import com.project.ecommerve.model.Tag;
import com.project.ecommerve.service.TagService;

@RestController
@RequestMapping("/tag")
public class TagController {

  private final TagService tagService;

  @Autowired
  public TagController(final TagService tagService) {
    this.tagService = tagService;
  }

  @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<Tag>> getAllTags() throws NoTagsAvailableException {
    final List<Tag> tags = tagService.retrieveAllTags();
    return new ResponseEntity<>(tags, HttpStatus.OK);
  }

  @PostMapping(value = "/add", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Tag> addTag(@RequestBody final TagDto tagDto)
      throws TagAlreadyExistsException {
    final Tag tag = tagService.persistTag(new Tag(tagDto));
    return new ResponseEntity<>(tag, HttpStatus.CREATED);
  }

  @PutMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Tag> updateTag(@RequestBody final TagDto tagDto)
      throws TagDoesNotExistsException {
    final Tag tag = tagService.updateTag(new Tag(tagDto));
    return new ResponseEntity<>(tag, HttpStatus.CREATED);
  }

  @DeleteMapping(value = "/delete", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<TagDto> deleteTag(@RequestParam final String name)
      throws TagDoesNotExistsException {
    final TagDto tagDto = tagService.deleteTag(name);
    return new ResponseEntity<>(tagDto, HttpStatus.OK);
  }
}
