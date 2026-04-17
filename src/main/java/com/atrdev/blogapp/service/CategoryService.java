package com.atrdev.blogapp.service;

import com.atrdev.blogapp.dto.CategoryDTO;

import java.util.List;

public interface CategoryService {
    List<CategoryDTO> listCategories();
}
