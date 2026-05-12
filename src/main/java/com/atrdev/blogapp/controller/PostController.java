package com.atrdev.blogapp.controller;

import com.atrdev.blogapp.dto.PostRequest;
import com.atrdev.blogapp.dto.PostResponse;
import com.atrdev.blogapp.dto.UpdatePostRequest;
import com.atrdev.blogapp.entity.User;
import com.atrdev.blogapp.service.PostService;
import com.atrdev.blogapp.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/posts")
public class PostController {

    private final PostService postService;
    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<PostResponse>> getAllPosts(
            @RequestParam(required = false) UUID categoryId,
            @RequestParam(required = false) UUID tagId) {
        return ResponseEntity.ok(postService.getAllPosts(categoryId, tagId));
    }

    @GetMapping(path = "/drafts")
    public ResponseEntity<List<PostResponse>> getAllDrafts(@RequestAttribute UUID userId) {
        User loggedInYser = userService.getUserById(userId);
        return ResponseEntity.ok(postService.getDraftPOst(loggedInYser));
    }

    @PostMapping
    public ResponseEntity<PostResponse> createPost(@Valid @RequestBody PostRequest postRequest,
                                                   @RequestAttribute UUID userId) {
        User loggedInYser = userService.getUserById(userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(postService.createPost(loggedInYser, postRequest));
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<PostResponse> updatePost(@PathVariable UUID id,
                                                   @Valid @RequestBody UpdatePostRequest updatePostRequest) {
        return ResponseEntity.ok(postService.updatePost(id, updatePostRequest));
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<PostResponse> getPost(@PathVariable UUID id) {
        return ResponseEntity.ok(postService.getPost(id));
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable UUID id) {
        postService.deletePost(id);
        return ResponseEntity.noContent().build();
    }

}
