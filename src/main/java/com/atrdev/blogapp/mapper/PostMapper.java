package com.atrdev.blogapp.mapper;

import com.atrdev.blogapp.dto.CreatePostRequest;
import com.atrdev.blogapp.dto.PostRequest;
import com.atrdev.blogapp.dto.PostResponse;
import com.atrdev.blogapp.entity.Post;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {CategoryMapper.class, TagMapper.class})
public interface PostMapper {

    @Mapping(target = "author", source = "author")
    @Mapping(target = "category", source = "category")
    @Mapping(target = "tags", source = "tags")
    @Mapping(target = "postStatus", source = "status")
    PostResponse toResponse(Post post);

    List<PostResponse> toResponseList(List<Post> posts);

    CreatePostRequest toCreatePostRequest(PostRequest postRequest);
}
