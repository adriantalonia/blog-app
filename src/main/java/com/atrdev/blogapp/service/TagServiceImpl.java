package com.atrdev.blogapp.service;

import com.atrdev.blogapp.dto.TagResponse;
import com.atrdev.blogapp.entity.Tag;
import com.atrdev.blogapp.exception.ResourceNotFoundException;
import com.atrdev.blogapp.mapper.TagMapper;
import com.atrdev.blogapp.repository.PostRepository;
import com.atrdev.blogapp.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;
    private final PostRepository postRepository;
    private final TagMapper tagMapper;

    @Override
    @Transactional(readOnly = true)
    public List<TagResponse> getAllTags() {
        return tagMapper.toResponseList(tagRepository.findAllWithPosts());
    }

    @Override
    @Transactional
    public List<TagResponse> createTags(Set<String> tagNames) {
        List<Tag> existingTags = tagRepository.findByNameIn(tagNames);
        Set<String> existingTagNames = existingTags.stream().map(Tag::getName).collect(Collectors.toSet());
        List<Tag> newTags = tagNames.stream()
                .filter(name -> !existingTagNames.contains(name))
                .map(name -> Tag.builder()
                        .name(name)
                        .posts(new HashSet<>())
                        .build()).toList();

        List<Tag> savedTags = new ArrayList<>();
        if (!newTags.isEmpty()) {
            savedTags = tagRepository.saveAll(newTags);
        }

        savedTags.addAll(existingTags);

        return tagMapper.toResponseList(savedTags);
    }

    @Override
    @Transactional
    public void deleteTag(UUID id) {
        Tag tag = tagRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tag", id));
        // 1) first approach - lazy loading
        /* if (!tag.getPosts().isEmpty()) {
            throw new IllegalStateException("Cannot delete tag with posts");
        }*/

        // 2) use a count query
        if (postRepository.existsById(id)) {
            throw new IllegalStateException("Cannot delete tag with posts");
        }
        tagRepository.delete(tag);
    }

    @Override
    @Transactional(readOnly = true)
    public Tag getTabById(UUID id) {
        return tagRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Tag", id));
    }

    @Override
    public List<Tag> getTagByIds(Set<UUID> ids) {
        List<Tag> foundTags = tagRepository.findAllById(ids);
        if (foundTags.size() != ids.size()) {
            throw new ResourceNotFoundException("No all specified tag IDs exist, Tags", null);
        }
        return foundTags;
    }
}
