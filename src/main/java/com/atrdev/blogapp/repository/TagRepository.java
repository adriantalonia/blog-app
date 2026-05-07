package com.atrdev.blogapp.repository;

import com.atrdev.blogapp.entity.Tag;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TagRepository extends JpaRepository<Tag, UUID> {

    @EntityGraph(attributePaths = {"posts"})
    @Query("SELECT c FROM Tag c")
    List<Tag> findAllWithPosts();
}
