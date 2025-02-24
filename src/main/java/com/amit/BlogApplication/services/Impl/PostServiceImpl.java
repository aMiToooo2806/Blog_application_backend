package com.amit.BlogApplication.services.Impl;
import com.amit.BlogApplication.entities.Category;
import com.amit.BlogApplication.entities.Posts;
import com.amit.BlogApplication.entities.Users;
import com.amit.BlogApplication.exceptations.ResourceNotFoundException;
import com.amit.BlogApplication.payloads.PostDto;
import com.amit.BlogApplication.repositories.CategoryRepo;
import com.amit.BlogApplication.repositories.PostRepo;
import com.amit.BlogApplication.repositories.UserRepo;
import com.amit.BlogApplication.services.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepo postRepo;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private CategoryRepo categoryRepo;

    @Override
    public PostDto createPosts(PostDto postDto,Integer userId, Integer categoryId) {

        Users users = this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("Users", "user id", userId));
        Category category = this.categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "category id", categoryId));

        Posts post = this.modelMapper.map(postDto, Posts.class);
        post.setImageName("default.png");
        post.setAddedDate(new Date());
        post.setUsers(users);
        post.setCategory(category);

        Posts saved = this.postRepo.save(post);
        return this.modelMapper.map(saved,PostDto.class);

    }

    @Override
    public Posts updatePosts(PostDto postDto, Integer postId) {
        return null;
    }

    @Override
    public void deletePosts(Integer postId) {

    }

    @Override
    public List<Posts> getAllPosts() {
        return List.of();
    }

    @Override
    public Posts getPostById(Integer postId) {
        return null;
    }

    @Override
    public List<PostDto> getPostByCategory(Integer categoryId) {
        Category category = this.categoryRepo.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "Category id", categoryId));
        List<Posts> byCategory = this.postRepo.findByCategory(category);
        if (byCategory.isEmpty()) {
            throw new ResourceNotFoundException("Posts", "category id", categoryId);
        }
        List<PostDto> postDtoList = byCategory.stream().map((post) -> this.modelMapper.map(post, PostDto.class)).toList();
        return postDtoList;
    }

    @Override
    public List<PostDto> getPostByUser(Integer userId) {
        Users user = this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("Users", "User id", userId));
        List<Posts> userList = this.postRepo.findByUsers(user);
        if (userList.isEmpty()) {
            throw new ResourceNotFoundException("Posts", "User id", userId);
        }
        List<PostDto> postDtos = userList.stream().map((post) -> this.modelMapper.map(post, PostDto.class)).toList();
        return postDtos;
    }

    @Override
    public List<Posts> searchPosts(String keyword) {
        return List.of();
    }
}
