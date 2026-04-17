package com.atrdev.blogapp.service;

import com.atrdev.blogapp.dto.CategoryDTO;
import com.atrdev.blogapp.mapper.CategoryMapper;
import com.atrdev.blogapp.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public List<CategoryDTO> listCategories() {
        return categoryMapper.toCategoryDTOList(categoryRepository.findAllWithPostCount());
    }
}
