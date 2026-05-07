package com.atrdev.blogapp.service;

import com.atrdev.blogapp.dto.TagResponse;
import com.atrdev.blogapp.mapper.TagMapper;
import com.atrdev.blogapp.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;
    private final TagMapper tagMapper;

    @Override
    @Transactional(readOnly = true)
    public List<TagResponse> getAllTags() {
        return tagMapper.toResponseList(tagRepository.findAllWithPosts());
    }
}
