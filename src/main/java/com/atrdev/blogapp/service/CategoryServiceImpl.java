package com.atrdev.blogapp.service;

import com.atrdev.blogapp.dto.CategoryDTO;
import com.atrdev.blogapp.dto.CategoryRequest;
import com.atrdev.blogapp.mapper.CategoryMapper;
import com.atrdev.blogapp.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    @Transactional(readOnly = true)
    public List<CategoryDTO> listCategories() {
        return categoryMapper.toCategoryDTOList(categoryRepository.findAllWithPostCount());
    }

    @Override
    @Transactional
    public CategoryDTO createCategory(CategoryRequest categoryRequest) {
        if (categoryRepository.existsByNameIgnoreCase(categoryRequest.getName())) {
            throw new IllegalArgumentException("Category already exists with name: " + categoryRequest.getName());
        }

        return categoryMapper.categoryToCategoryDTO(categoryRepository.save(categoryMapper.categoryRequestToCategory(categoryRequest)));
    }

    /*@Override
    public CategoryDTO createCategory(CategoryRequest categoryRequest) {
        try {
            return categoryMapper.categoryToCategoryDto(
                    categoryRepository.save(
                            categoryMapper.categoryRequestToCategory(categoryRequest)
                    )
            );
        } catch (DataIntegrityViolationException ex) {
            throw new DuplicateCategoryException(categoryRequest.getName());
        }
    }*/
}
