package com.atrdev.blogapp.service;

import com.atrdev.blogapp.dto.TagResponse;
import com.atrdev.blogapp.entity.Tag;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface TagService {
    List<TagResponse> getAllTags();
    List<TagResponse> createTags(Set<String> tagNames);
    void deleteTag(UUID ID);
    Tag getTabById(UUID id);
}
