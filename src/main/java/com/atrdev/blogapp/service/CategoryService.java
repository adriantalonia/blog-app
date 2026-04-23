package com.atrdev.blogapp.service;

import com.atrdev.blogapp.dto.CategoryDTO;
import com.atrdev.blogapp.dto.CategoryRequest;

import java.util.List;
import java.util.UUID;

public interface CategoryService {
    List<CategoryDTO> listCategories();
    CategoryDTO createCategory(CategoryRequest categoryRequest);
    void deleteCategory(UUID id);
}
