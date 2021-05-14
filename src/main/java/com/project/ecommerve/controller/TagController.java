package com.project.ecommerve.controller;

import com.project.ecommerve.dto.TagDto;
import com.project.ecommerve.exception.NoTagsAvailableException;
import com.project.ecommerve.exception.TagAlreadyExistsException;
import com.project.ecommerve.exception.TagDoesNotExistsException;
import com.project.ecommerve.model.Tag;
import com.project.ecommerve.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/tag")
public class TagController {

    private final TagService tagService;

    @Autowired
    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping(value = "/")
    public ResponseEntity<List<Tag>> getAllTags() throws NoTagsAvailableException {
        List<Tag> tags = tagService.retrieveAllTags();
        return new ResponseEntity<>(tags, HttpStatus.OK);
    }

    @PostMapping(value = "/add")
    public ResponseEntity<Tag> addTag(@RequestBody TagDto tagDto) throws TagAlreadyExistsException {
        Tag tag = tagService.persistTag(new Tag(tagDto));
        return new ResponseEntity<>(tag, HttpStatus.CREATED);
    }

    @PutMapping(value = "/update")
    public ResponseEntity<Tag> updateTag(@RequestBody TagDto tagDto) throws TagDoesNotExistsException {
        Tag tag = tagService.updateTag(new Tag(tagDto));
        return new ResponseEntity<>(tag, HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/delete")
    public ResponseEntity<TagDto> deleteTag(@RequestParam String name) throws TagDoesNotExistsException {
        TagDto tagDto = tagService.deleteTag(name);
        return new ResponseEntity<>(tagDto, HttpStatus.OK);
    }
}
