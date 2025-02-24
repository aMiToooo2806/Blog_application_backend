package com.amit.BlogApplication.services;

import com.amit.BlogApplication.entities.Posts;
import com.amit.BlogApplication.payloads.PostDto;
import jakarta.persistence.criteria.CriteriaBuilder;

import java.util.List;

public interface PostService {

    PostDto createPosts(PostDto postDto,Integer userId, Integer categoryId);

    Posts updatePosts(PostDto postDto, Integer postId);

    void deletePosts(Integer postId);

    List<Posts>getAllPosts();

    Posts getPostById(Integer postId);

    List<Posts>getPostByCategory(Integer categoryId);

    List<Posts>getPostByUser(Integer userId);

    List<Posts>searchPosts(String keyword);
}
