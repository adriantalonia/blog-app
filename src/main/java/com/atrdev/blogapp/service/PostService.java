package com.atrdev.blogapp.service;

import com.atrdev.blogapp.dto.PostRequest;
import com.atrdev.blogapp.dto.PostResponse;
import com.atrdev.blogapp.dto.UpdatePostRequest;
import com.atrdev.blogapp.entity.User;

import java.util.List;
import java.util.UUID;

public interface PostService {
    List<PostResponse> getAllPosts(UUID categoryId, UUID tagId);

    List<PostResponse> getDraftPOst(User user);
    PostResponse createPost(User user, PostRequest postRequest);
    PostResponse updatePost(UUID ID, UpdatePostRequest updatePostRequest);
    PostResponse getPost(UUID id);
    void deletePost(UUID id);
}
