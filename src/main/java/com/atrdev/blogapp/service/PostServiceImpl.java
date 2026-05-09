package com.atrdev.blogapp.service;

import com.atrdev.blogapp.dto.PostResponse;
import com.atrdev.blogapp.entity.Category;
import com.atrdev.blogapp.entity.Tag;
import com.atrdev.blogapp.enums.PostStatus;
import com.atrdev.blogapp.mapper.PostMapper;
import com.atrdev.blogapp.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final CategoryService categoryService;
    private final TagService tagService;
    private final PostMapper postMapper;

    @Override
    @Transactional(readOnly = true)
    public List<PostResponse> getAllPosts(UUID categoryId, UUID tagId) {
        if (categoryId != null && tagId != null) {
            Category category = categoryService.getCategoryById(categoryId);
            Tag tag = tagService.getTabById(tagId);

            return postMapper.toResponseList(
                    postRepository.findAllByStatusAndCategoryAndTagsContaining(
                            PostStatus.PUBLISHED,
                            category,
                            tag
                    )
            );
        }

        if (categoryId != null) {
            Category category = categoryService.getCategoryById(categoryId);
            return postMapper.toResponseList(
                    postRepository.findAllByStatusAndCategory(
                            PostStatus.PUBLISHED,
                            category
                    )
            );
        }

        if (tagId != null) {
            Tag tag = tagService.getTabById(tagId);
            return postMapper.toResponseList(
                    postRepository.findAllByStatusAndTagsContaining(
                            PostStatus.PUBLISHED,
                            tag
                    )
            );
        }

        return postMapper.toResponseList(postRepository.findAllByStatus(PostStatus.PUBLISHED));
    }
}
