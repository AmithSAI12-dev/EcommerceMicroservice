package com.project.ecommerve.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.project.ecommerve.dto.TagDto;
import com.project.ecommerve.exception.NoTagsAvailableException;
import com.project.ecommerve.exception.TagAlreadyExistsException;
import com.project.ecommerve.exception.TagDoesNotExistsException;
import com.project.ecommerve.model.Tag;
import com.project.ecommerve.repository.TagRepository;

@ExtendWith(MockitoExtension.class)
class TagServiceImplTest {

  @Mock private TagRepository tagRepositoryMock;
  private static Tag tagMock;
  @InjectMocks private TagServiceImpl tagService;

  @BeforeAll
  static void setUp() {
    tagMock = new Tag();
    tagMock.setName("Mock Name");
    tagMock.setDescription("Mock Description");
  }

  @Test
  void testRetrieveAllTags_returnsTagList() throws NoTagsAvailableException {
    when(tagRepositoryMock.findAll()).thenReturn(Collections.singletonList(tagMock));
    final List<Tag> tags = tagService.retrieveAllTags();
    assertNotNull(tags, "Checking Not Null");
    verify(tagRepositoryMock, atMostOnce()).findAll();
  }

  @Test
  void testRetrieveAllTags_throwsException_whenThereIsNoContent() {
    when(tagRepositoryMock.findAll()).thenReturn(Collections.emptyList());
    assertThrows(NoTagsAvailableException.class, () -> tagService.retrieveAllTags());
    verify(tagRepositoryMock, atMostOnce()).findAll();
  }

  @Test
  void testPersistTag_returnsTag() throws TagAlreadyExistsException {
    when(tagRepositoryMock.findById(anyString())).thenReturn(Optional.empty());
    when(tagRepositoryMock.save(any(Tag.class))).thenReturn(tagMock);
    final Tag tag = tagService.persistTag(tagMock);
    assertNotNull(tag, "Checking Not Null");
    verify(tagRepositoryMock, atMostOnce()).findById(anyString());
    verify(tagRepositoryMock, atMostOnce()).save(tagMock);
  }

  @Test
  void testPersistTag_throwsException_whenTagAlreadyExists() throws TagAlreadyExistsException {
    when(tagRepositoryMock.findById(anyString())).thenReturn(Optional.of(tagMock));
    assertThrows(TagAlreadyExistsException.class, () -> tagService.persistTag(tagMock));
    verify(tagRepositoryMock, atMostOnce()).findById(anyString());
  }

  @Test
  void testUpdateTag_returnsTag() throws TagDoesNotExistsException {
    when(tagRepositoryMock.findById(anyString())).thenReturn(Optional.of(tagMock));
    when(tagRepositoryMock.save(any(Tag.class))).thenReturn(tagMock);
    final Tag tag = tagService.updateTag(tagMock);
    assertNotNull(tag, "Checking Not Null");
    verify(tagRepositoryMock, atMostOnce()).findById(anyString());
    verify(tagRepositoryMock, atMostOnce()).save(tagMock);
  }

  @Test
  void testUpdateTag_throwsException_whenTagDoesNotExists() throws TagDoesNotExistsException {
    when(tagRepositoryMock.findById(anyString())).thenReturn(Optional.empty());
    assertThrows(TagDoesNotExistsException.class, () -> tagService.updateTag(tagMock));
    verify(tagRepositoryMock, atMostOnce()).findById(anyString());
  }

  @Test
  void testDeleteTag_returnsTagDto() throws TagDoesNotExistsException {
    when(tagRepositoryMock.findById(anyString())).thenReturn(Optional.of(tagMock));
    doNothing().when(tagRepositoryMock).deleteById(anyString());
    final TagDto tagDto = tagService.deleteTag("Mock Name");
    assertNotNull(tagDto, "Checking Not Null");
    verify(tagRepositoryMock, atMostOnce()).findById(anyString());
    verify(tagRepositoryMock, atMostOnce()).deleteById(anyString());
  }

  @Test
  void testDeleteTag_throwsException_whenTagDoesNotExists() throws TagDoesNotExistsException {
    when(tagRepositoryMock.findById(anyString())).thenReturn(Optional.empty());
    assertThrows(TagDoesNotExistsException.class, () -> tagService.deleteTag("Mock Name"));
    verify(tagRepositoryMock, atMostOnce()).findById(anyString());
  }
}
