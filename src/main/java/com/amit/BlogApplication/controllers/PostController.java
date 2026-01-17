package com.amit.BlogApplication.controllers;

import com.amit.BlogApplication.payloads.PostDto;
import com.amit.BlogApplication.payloads.PostResponse;
import com.amit.BlogApplication.services.PostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/")
public class PostController {

    @Autowired
    private PostService postService;

    // Create Post: ADMIN or SAME USER
    @PreAuthorize("hasAnyRole('ADMIN' OR @userSecurity.isOwner(#userId))")
    @PostMapping("/user/{userId}/category/{categoryId}/posts")
    public ResponseEntity<PostDto>createPost(@RequestBody PostDto postDto, @PathVariable Integer userId,@PathVariable Integer categoryId)
    {
        log.info("POST /api/posts/user/{}/category/{} called", userId, categoryId);
        PostDto createdPost = this.postService.createPosts(postDto, userId, categoryId);
        log.info("Post created successfully with id={}", createdPost.getPostId());

        return new ResponseEntity<PostDto>(createdPost, HttpStatus.CREATED);
    }

    //Get by user: ADMIN or same user
    @PreAuthorize("hasAnyRole('ADMIN' OR @userSecurity.isOwner(#userId))")
    @GetMapping("/user/{userId}/posts")
    public ResponseEntity<List<PostDto>>getPostsByUser(@PathVariable Integer userId)
    {
        List<PostDto> postByUser = this.postService.getPostByUser(userId);
        return new ResponseEntity<List<PostDto>>(postByUser,HttpStatus.OK);
    }

    // Get by category: ADMIN or USER (any authenticated)
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping("/category/{categoryId}/posts")
    public ResponseEntity<List<PostDto>>getPostsByCategory(@PathVariable Integer categoryId)
    {
        List<PostDto> postByCategory = this.postService.getPostByCategory(categoryId);
        return new ResponseEntity<>(postByCategory,HttpStatus.OK);
    }

    // Get all posts: ADMIN only
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping("/AllPosts")
    //http://localhost:8080/api/AllPosts?pageNumber=0&pageSize=4 (For the paging)
    public ResponseEntity<PostResponse>getAllPosts(@RequestParam(value = "pageNumber",defaultValue = "0",required = false) Integer pageNumber
    , @RequestParam(value = "pageSize",defaultValue = "4",required = false) Integer pageSize)
    {
        PostResponse allPosts = this.postService.getAllPosts(pageNumber, pageSize);
        return new ResponseEntity<PostResponse>(allPosts,HttpStatus.OK);
    }

    //Get post by Id
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping("/post/{postId}")
    public ResponseEntity<PostDto> getPostByPostId(@PathVariable Integer postId)
    {
        PostDto postById = this.postService.getPostById(postId);
        return new ResponseEntity<>(postById,HttpStatus.OK);
    }

    // Update Post: ADMIN or Post Owner
    @PreAuthorize("hasRole('ADMIN') OR @postSecurity.isPostOwner(#postId)")
    @PutMapping("/update/{postId}")
    public ResponseEntity<PostDto>updatePost(@RequestBody PostDto postDto, @PathVariable Integer postId)
    {
        PostDto UpdatedPost = postService.updatePosts(postDto, postId);
        return new ResponseEntity<>(UpdatedPost,HttpStatus.OK);
    }

    // Delete Post: ADMIN or Post Owner
    @PreAuthorize("hasRole('ADMIN') OR @postSecurity.isPostOwner(#postId)")
    @DeleteMapping("/delete/{postId}")
    public ResponseEntity<?>deletePost(@PathVariable Integer postId)
    {
         return postService.deletePosts(postId);

    }
}
