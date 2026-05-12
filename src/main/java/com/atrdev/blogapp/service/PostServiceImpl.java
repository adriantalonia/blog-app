package com.atrdev.blogapp.service;

import com.atrdev.blogapp.dto.PostRequest;
import com.atrdev.blogapp.dto.PostResponse;
import com.atrdev.blogapp.dto.UpdatePostRequest;
import com.atrdev.blogapp.entity.Category;
import com.atrdev.blogapp.entity.Post;
import com.atrdev.blogapp.entity.Tag;
import com.atrdev.blogapp.entity.User;
import com.atrdev.blogapp.enums.PostStatus;
import com.atrdev.blogapp.exception.ResourceNotFoundException;
import com.atrdev.blogapp.mapper.PostMapper;
import com.atrdev.blogapp.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final CategoryService categoryService;
    private final TagService tagService;
    private final PostMapper postMapper;

    private static final int DEFAULT_WORDS_PER_MINUTE = 200;

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

    @Override
    @Transactional(readOnly = true)
    public List<PostResponse> getDraftPOst(User user) {
        return postMapper.toResponseList(postRepository.findAllByAuthorAndStatus(user, PostStatus.DRAFT));
    }

    @Override
    @Transactional
    public PostResponse createPost(User user, PostRequest postRequest) {
        Category category = categoryService.getCategoryById(postRequest.getCategoryId());
        List<Tag> tags = tagService.getTagByIds(postRequest.getTagsIds());

        Post post = new Post();
        post.setTitle(postRequest.getTitle());
        post.setContent(postRequest.getContent());
        post.setStatus(postRequest.getStatus());
        post.setAuthor(user);
        post.setReadingTime(calculateReadingTime(post.getContent()));
        post.setCategory(category);
        post.setTags(new HashSet<>(tags));

        return postMapper.toResponse(postRepository.save(post));
    }

    @Override
    @Transactional
    public PostResponse updatePost(UUID id, UpdatePostRequest updatePostRequest) {
        Post existingPost = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", id));

        existingPost.setTitle(updatePostRequest.getTitle());
        existingPost.setContent(updatePostRequest.getContent());
        existingPost.setStatus(updatePostRequest.getStatus());
        existingPost.setReadingTime(calculateReadingTime(existingPost.getContent()));

        UUID updatedCategoryId = updatePostRequest.getCategoryId();
        if (!existingPost.getCategory().getId().equals(updatedCategoryId)) {
            Category category = categoryService.getCategoryById(updatePostRequest.getCategoryId());
            existingPost.setCategory(category);
        }

        Set<UUID> existingTagIds = existingPost.getTags().stream().map(Tag::getId).collect(Collectors.toSet());
        Set<UUID> updatedTagIds = updatePostRequest.getTagsIds();
        if (!existingTagIds.containsAll(updatedTagIds)) {
            List<Tag> tags = tagService.getTagByIds(updatePostRequest.getTagsIds());
            existingPost.setTags(new HashSet<>(tags));
        }
        return postMapper.toResponse(postRepository.save(existingPost));
    }

    @Override
    @Transactional(readOnly = true)
    public PostResponse getPost(UUID id) {
        return postMapper.toResponse(postRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post", id)));
    }

    @Override
    @Transactional
    public void deletePost(UUID id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", id));
        postRepository.delete(post);
    }

    private Integer calculateReadingTime(String content) {
        if (content == null || content.isEmpty()) {
            return 0;
        }
        int wordCount = content.trim().split(" ").length;
        return (int) Math.ceil((double) wordCount / DEFAULT_WORDS_PER_MINUTE);
    }
}
