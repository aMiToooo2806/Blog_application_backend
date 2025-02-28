package com.amit.BlogApplication.services.Impl;
import com.amit.BlogApplication.entities.Category;
import com.amit.BlogApplication.entities.Posts;
import com.amit.BlogApplication.entities.Users;
import com.amit.BlogApplication.exceptations.ResourceNotFoundException;
import com.amit.BlogApplication.payloads.PostDto;
import com.amit.BlogApplication.payloads.PostResponse;
import com.amit.BlogApplication.repositories.CategoryRepo;
import com.amit.BlogApplication.repositories.PostRepo;
import com.amit.BlogApplication.repositories.UserRepo;
import com.amit.BlogApplication.services.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
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
    public PostDto updatePosts(PostDto postDto, Integer postId) {
        Posts post = this.postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Posts", "Post Id", postId));
        post.setPostContent(postDto.getPostContent());
        post.setPostTitle(postDto.getPostTitle());
        post.setImageName(postDto.getImageName());
        Posts updatedPost = postRepo.save(post);
        return modelMapper.map(updatedPost,PostDto.class);
    }

    @Override
    public ResponseEntity<PostDto> deletePosts(Integer postId) {
        Posts post = this.postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Posts", "Post Id", postId));
        postRepo.delete(post);
        PostDto dto = modelMapper.map(post, PostDto.class);
        return new ResponseEntity<>(dto,HttpStatus.OK);
    }

    @Override
    public PostResponse getAllPosts(Integer pageNumber, Integer pageSize) {
        PageRequest p = PageRequest.of(pageNumber, pageSize);
        Page<Posts> all = this.postRepo.findAll(p);
        List<Posts> AllPosts = all.getContent();

        List<PostDto> postDtos = AllPosts.stream().map((post) -> this.modelMapper.map(post, PostDto.class)).toList();

        PostResponse postResponse = new PostResponse();
        postResponse.setContent(postDtos);
        postResponse.setPageNumber(all.getNumber());
        postResponse.setPageSize(all.getSize());
        postResponse.setTotalElements(all.getTotalElements());
        postResponse.setTotalPages(all.getTotalPages());
        postResponse.setLastPage(all.isLast());

        return postResponse;
    }

    @Override
    public PostDto getPostById(Integer postId) {
        Posts post = this.postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Posts", "Post Id", postId));
        return this.modelMapper.map(post,PostDto.class);
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
