package com.atrdev.blogapp.service;

import com.atrdev.blogapp.dto.TagResponse;

import java.util.List;
import java.util.Set;

public interface TagService {
    List<TagResponse> getAllTags();
    List<TagResponse> createTags(Set<String> tagNames);
}
