package com.atrdev.blogapp.controller;

import com.atrdev.blogapp.dto.TagRequest;
import com.atrdev.blogapp.dto.TagResponse;
import com.atrdev.blogapp.service.TagService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/tags")
@RequiredArgsConstructor
public class TagController {

    private final TagService tagService;

    @GetMapping
    public ResponseEntity<List<TagResponse>> getAllTags() {
        return ResponseEntity.ok().body(tagService.getAllTags());
    }

    @PostMapping
    public ResponseEntity<List<TagResponse>> addTags(@RequestBody @Valid TagRequest tagRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(tagService.createTags(tagRequest.getNames()));
    }
}
