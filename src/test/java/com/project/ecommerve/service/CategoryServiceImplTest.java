package com.project.ecommerve.service;

import com.project.ecommerve.dto.CategoryDto;
import com.project.ecommerve.exception.CategoryDetailAlreadyExistsException;
import com.project.ecommerve.exception.CategoryDetailDoesNotExistsException;
import com.project.ecommerve.exception.NoCategoryAvailableException;
import com.project.ecommerve.model.Brand;
import com.project.ecommerve.model.Category;
import com.project.ecommerve.repository.CategoryRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryServiceImplTest {

    @Mock
    private CategoryRepository categoryRepositoryMock;
    @Mock
    private Page<Category> categoryPageMock;
    @Mock
    private static Category categoryMock;
    @InjectMocks
    private CategoryServiceImpl categoryService;

    @BeforeAll
    void setUp() {
        categoryMock = new Category();
        categoryMock.setName(" Mock Name");
    }

    @Test
    void testRetrieveAllCategories_returnsCategoryList() throws NoCategoryAvailableException {
        when(categoryRepositoryMock.findAll(any(Pageable.class)))
                .thenReturn(categoryPageMock);
        when(categoryPageMock.getContent()).thenReturn(Collections.singletonList(categoryMock));
        List<Category> categories = categoryService.retrieveAllCategories(1, 1, "Mock Sort");
        assertNotNull(categories);
        verify(categoryRepositoryMock, atMostOnce()).findAll(any(Pageable.class));
        verify(categoryPageMock, atMostOnce()).getContent();
    }

    @Test
    void testRetrieveAllCategories_throwsException_whenPageIsEmpty() {
        when(categoryRepositoryMock.findAll(any(Pageable.class)))
                .thenThrow(new NoCategoryAvailableException("Mock Exception"));
        assertThrows(NoCategoryAvailableException.class,
                () -> categoryService.retrieveAllCategories(1,1,"Mock Sort"));
        verify(categoryRepositoryMock, atMostOnce()).findAll(any(Pageable.class));
    }

    @Test
    void testPersistCategory_returnsCategory() throws CategoryDetailAlreadyExistsException {
        when(categoryRepositoryMock.findById(anyString()))
                .thenReturn(Optional.empty());
        when(categoryRepositoryMock.save(any(Category.class)))
                .thenReturn(categoryMock);
        Category category = categoryService.persistCategory(categoryMock);
        assertNotNull(category);
        verify(categoryRepositoryMock, atMostOnce()).findById(anyString());
        verify(categoryRepositoryMock, atMostOnce()).save(any(Category.class));
    }

    @Test
    void testPersistCategory_throwsException_whenCategoryExists() {
        when(categoryRepositoryMock.findById(anyString())).thenReturn(Optional.of(categoryMock));
        assertThrows(CategoryDetailAlreadyExistsException.class,
                () -> categoryService.persistCategory(categoryMock));
        verify(categoryRepositoryMock, atMostOnce()).findById(anyString());
    }

    @Test
    void testUpdateCategory_returnsCategory() throws CategoryDetailDoesNotExistsException {
        when(categoryRepositoryMock.findById(anyString()))
                .thenReturn(Optional.of(categoryMock));
        when(categoryRepositoryMock.save(any(Category.class)))
                .thenReturn(categoryMock);
        Category category = categoryService.updateCategory(categoryMock);
        assertNotNull(category);
        verify(categoryRepositoryMock, atMostOnce()).findById(anyString());
        verify(categoryRepositoryMock, atMostOnce()).save(any(Category.class));
    }

    @Test
    void testUpdateCategory_throwsException_whenCategoryDoesNotExists() {
        when(categoryRepositoryMock.findById(anyString()))
                .thenReturn(Optional.empty());
        assertThrows(CategoryDetailDoesNotExistsException.class,
                () -> categoryService.updateCategory(categoryMock));
        verify(categoryRepositoryMock, atMostOnce()).findById(anyString());
    }

    @Test
    void testDeleteCategory_returnsCategoryDto() throws CategoryDetailDoesNotExistsException {
        when(categoryRepositoryMock.findById(anyString()))
                .thenReturn(Optional.of(categoryMock));
        doNothing().when(categoryRepositoryMock).deleteById(anyString());
        CategoryDto categoryDto = categoryService.deleteCategory("Mock Name");
        assertNotNull(categoryDto);
        verify(categoryRepositoryMock, atMostOnce()).findById(anyString());
        verify(categoryRepositoryMock,atMostOnce()).deleteById(anyString());
    }

    @Test
    void testDeleteCategory_throwsException_whenCategoryDoesNotExists() {
        when(categoryRepositoryMock.findById(anyString()))
                .thenReturn(Optional.empty());
        assertThrows(CategoryDetailDoesNotExistsException.class,
                () -> categoryService.deleteCategory(anyString()));
        verify(categoryRepositoryMock, atMostOnce()).findById(anyString());
    }
}