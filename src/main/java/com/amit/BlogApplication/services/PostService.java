package com.amit.BlogApplication.services;

import com.amit.BlogApplication.entities.Posts;
import com.amit.BlogApplication.payloads.PostDto;
import com.amit.BlogApplication.payloads.PostResponse;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface PostService {

    PostDto createPosts(PostDto postDto,Integer userId, Integer categoryId);

    PostDto updatePosts(PostDto postDto, Integer postId);

    ResponseEntity<PostDto> deletePosts(Integer postId);

    PostResponse getAllPosts(Integer pageNumber, Integer pageSize);

    PostDto getPostById(Integer postId);

    List<PostDto>getPostByCategory(Integer categoryId);

    List<PostDto>getPostByUser(Integer userId);

    List<Posts>searchPosts(String keyword);
}
