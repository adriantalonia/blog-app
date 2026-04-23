package com.atrdev.blogapp.service;

import com.atrdev.blogapp.dto.CategoryDTO;
import com.atrdev.blogapp.dto.CategoryRequest;
import com.atrdev.blogapp.entity.Category;
import com.atrdev.blogapp.exception.ResourceNotFoundException;
import com.atrdev.blogapp.mapper.CategoryMapper;
import com.atrdev.blogapp.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

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

    @Override
    @Transactional
    public void deleteCategory(UUID id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category", id));

        if (!category.getPosts().isEmpty()) {
            throw new IllegalArgumentException("Category has posts associated with it");
        }
        categoryRepository.deleteById(id);
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
