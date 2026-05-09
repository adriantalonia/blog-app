package com.atrdev.blogapp.service;

import com.atrdev.blogapp.dto.PostResponse;

import java.util.List;
import java.util.UUID;

public interface PostService {
    List<PostResponse> getAllPosts(UUID categoryId, UUID tagId);
}
