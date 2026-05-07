package com.atrdev.blogapp.service;

import com.atrdev.blogapp.dto.TagResponse;

import java.util.List;

public interface TagService {
    List<TagResponse> getAllTags();
}
