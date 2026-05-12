package com.atrdev.blogapp.repository;

import com.atrdev.blogapp.dto.PostResponse;
import com.atrdev.blogapp.entity.Category;
import com.atrdev.blogapp.entity.Post;
import com.atrdev.blogapp.entity.Tag;
import com.atrdev.blogapp.entity.User;
import com.atrdev.blogapp.enums.PostStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PostRepository extends JpaRepository<Post, UUID> {
    boolean existsByTagsId(UUID tagId);

    List<Post> findAllByStatusAndCategoryAndTagsContaining(PostStatus status, Category category, Tag tag);
    List<Post> findAllByStatusAndCategory(PostStatus status, Category category);
    List<Post> findAllByStatusAndTagsContaining(PostStatus status, Tag tag);
    List<Post> findAllByStatus(PostStatus status);

    List<Post> findAllByAuthorAndStatus(User user, PostStatus postStatus);
}
