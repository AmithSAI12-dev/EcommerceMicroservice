package com.project.ecommerve.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collections;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.ecommerve.dto.TagDto;
import com.project.ecommerve.exception.NoTagsAvailableException;
import com.project.ecommerve.exception.TagAlreadyExistsException;
import com.project.ecommerve.exception.TagDoesNotExistsException;
import com.project.ecommerve.model.Tag;
import com.project.ecommerve.service.TagServiceImpl;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(TagController.class)
class TagControllerTest {

  @MockBean private TagServiceImpl tagService;
  @Autowired private MockMvc mockMvc;

  @Test
  void testGetAllTags_returnsStatusOK() throws Exception {
    when(tagService.retrieveAllTags()).thenReturn(Collections.singletonList(new Tag()));
    mockMvc.perform(get("/tag/")).andExpect(status().isOk());
    verify(tagService, atMostOnce()).retrieveAllTags();
  }

  @Test
  void testGetAllTags_returnsNoContent_whenExceptionIsThrown() throws Exception {
    when(tagService.retrieveAllTags()).thenThrow(new NoTagsAvailableException("Mock Exception"));
    mockMvc.perform(get("/tag/")).andExpect(status().isNoContent());
    verify(tagService, atMostOnce()).retrieveAllTags();
  }

  @Test
  void testAddTag_returnsStatusCreated() throws Exception {
    when(tagService.persistTag(any(Tag.class))).thenReturn(new Tag());
    mockMvc
        .perform(
            post("/tag/add")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(new ObjectMapper().writeValueAsString(new Tag())))
        .andExpect(status().isCreated());
    verify(tagService, atMostOnce()).persistTag(any(Tag.class));
  }

  @Test
  void testAddTag_returnsStatusBadRequest_whenExceptionIsThrown() throws Exception {
    when(tagService.persistTag(any(Tag.class)))
        .thenThrow(new TagAlreadyExistsException("Mock Exception"));
    mockMvc
        .perform(
            post("/tag/add")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(new ObjectMapper().writeValueAsString(new Tag())))
        .andExpect(status().isBadRequest());
    verify(tagService, atMostOnce()).persistTag(any(Tag.class));
  }

  @Test
  void testUpdateTag_returnsStatusCreated() throws Exception {
    when(tagService.updateTag(any(Tag.class))).thenReturn(new Tag());
    mockMvc
        .perform(
            put("/tag/update")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(new ObjectMapper().writeValueAsString(new Tag())))
        .andExpect(status().isCreated());
    verify(tagService, atMostOnce()).updateTag(any(Tag.class));
  }

  @Test
  void testUpdateTag_returnsStatusBadRequest_whenExceptionIsThrown() throws Exception {
    when(tagService.updateTag(any(Tag.class)))
        .thenThrow(new TagDoesNotExistsException("Mock Exception"));
    mockMvc
        .perform(
            put("/tag/update")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(new ObjectMapper().writeValueAsString(new Tag())))
        .andExpect(status().isBadRequest());
    verify(tagService, atMostOnce()).updateTag(any(Tag.class));
  }

  @Test
  void testDeleteTag_returnsStatusOk() throws Exception {
    when(tagService.deleteTag(anyString())).thenReturn(new TagDto());
    mockMvc.perform(delete("/tag/delete").param("name", "MockName")).andExpect(status().isOk());
    verify(tagService, atMostOnce()).deleteTag(anyString());
  }

  @Test
  void testDeleteTag_returnsStatusBadRequest_whenExceptionIsThrown() throws Exception {
    when(tagService.deleteTag(anyString()))
        .thenThrow(new TagDoesNotExistsException("Mock Exception"));
    mockMvc
        .perform(delete("/tag/delete").param("name", "Mock Name"))
        .andExpect(status().isBadRequest());
    verify(tagService, atMostOnce()).deleteTag(anyString());
  }
}
