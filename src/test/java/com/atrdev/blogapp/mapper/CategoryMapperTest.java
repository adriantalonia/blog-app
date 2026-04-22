package com.atrdev.blogapp.mapper;

import com.atrdev.blogapp.dto.CategoryDTO;
import com.atrdev.blogapp.entity.Category;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class CategoryMapperTest {

    @Autowired
    private CategoryMapper categoryMapper;

    @Test
    public void shouldMapCategoryToCategoryDTOWithZeroPostCount() {
        Category category = Category.builder()
                .name("Test1")
                .description("Category test description")
                .posts(null) // Simulate null posts
                .build();

        CategoryDTO categoryDTO = categoryMapper.categoryToCategoryDTO(category);

        assertNotNull(categoryDTO);
        assertEquals("Test1", categoryDTO.getName());
        assertEquals(0L, categoryDTO.getPostCount(), "postCount should be 0 even if posts is null");
    }

    @Test
    public void shouldMapCategoryToCategoryDTOWithZeroPostCountWhenPostsIsEmpty() {
        Category category = Category.builder()
                .name("Test2")
                .description("Category test description")
                .posts(new ArrayList<>()) // Simulate empty list
                .build();

        CategoryDTO categoryDTO = categoryMapper.categoryToCategoryDTO(category);

        assertNotNull(categoryDTO);
        assertEquals(0L, categoryDTO.getPostCount(), "postCount should be 0 when posts is empty");
    }
}
