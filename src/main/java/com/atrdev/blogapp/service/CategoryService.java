package com.atrdev.blogapp.service;

import com.atrdev.blogapp.dto.CategoryDTO;
import com.atrdev.blogapp.dto.CategoryRequest;

import java.util.List;

public interface CategoryService {
    List<CategoryDTO> listCategories();
    CategoryDTO createCategory(CategoryRequest categoryRequest);
}
