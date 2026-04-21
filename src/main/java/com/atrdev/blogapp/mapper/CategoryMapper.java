package com.atrdev.blogapp.mapper;

import com.atrdev.blogapp.dto.CategoryDTO;
import com.atrdev.blogapp.dto.CategoryRequest;
import com.atrdev.blogapp.entity.Category;
import com.atrdev.blogapp.entity.Post;
import com.atrdev.blogapp.enums.PostStatus;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CategoryMapper {

    @Mapping(target = "postCount", source = "posts", qualifiedByName = "calculatePostCount")
    CategoryDTO categoryToCategoryDTO(Category category);

    List<CategoryDTO> toCategoryDTOList(List<Category> categories);

    Category categoryRequestToCategory(CategoryRequest categoryRequest);

    @Named("calculatePostCount")
    default long calculatePostCount(List<Post> posts) {
        return posts == null ? 0 : posts.stream()
                                   .filter(post -> PostStatus.PUBLISHED.equals(post.getStatus()))
                                   .count();
    }
}
