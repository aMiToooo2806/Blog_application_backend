package com.amit.BlogApplication.controllers;

import com.amit.BlogApplication.payloads.PostDto;
import com.amit.BlogApplication.payloads.PostResponse;
import com.amit.BlogApplication.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.PrivateKey;
import java.util.List;

@RestController
@RequestMapping("/api/")
public class PostController {

    @Autowired
    private PostService postService;

    @PostMapping("/user/{userId}/category/{categoryId}/posts")
    public ResponseEntity<PostDto>createPost(@RequestBody PostDto postDto, @PathVariable Integer userId,@PathVariable Integer categoryId)
    {
        PostDto createdPost = this.postService.createPosts(postDto, userId, categoryId);
        return new ResponseEntity<PostDto>(createdPost, HttpStatus.CREATED);
    }

    //get posts by user
    @GetMapping("/user/{userId}/posts")
    public ResponseEntity<List<PostDto>>getPostsByUser(@PathVariable Integer userId)
    {
        List<PostDto> postByUser = this.postService.getPostByUser(userId);
        return new ResponseEntity<List<PostDto>>(postByUser,HttpStatus.OK);
    }

    //get posts by category
    @GetMapping("/category/{categoryId}/posts")
    public ResponseEntity<List<PostDto>>getPostsByCategory(@PathVariable Integer categoryId)
    {
        List<PostDto> postByCategory = this.postService.getPostByCategory(categoryId);
        return new ResponseEntity<>(postByCategory,HttpStatus.OK);
    }
    //Get All posts

    @GetMapping("/AllPosts")
    //http://localhost:8080/api/AllPosts?pageNumber=0&pageSize=4 (For the paging)
    public ResponseEntity<PostResponse>getAllPosts(@RequestParam(value = "pageNumber",defaultValue = "0",required = false) Integer pageNumber
    , @RequestParam(value = "pageSize",defaultValue = "4",required = false) Integer pageSize)
    {
        PostResponse allPosts = this.postService.getAllPosts(pageNumber, pageSize);
        return new ResponseEntity<PostResponse>(allPosts,HttpStatus.OK);
    }

    //Get post by Id
    @GetMapping("/post/{postId}")
    public ResponseEntity<PostDto>getPostById(@PathVariable Integer postId)
    {
        PostDto postById = this.postService.getPostById(postId);
        return new ResponseEntity<>(postById,HttpStatus.OK);
    }

    @PutMapping("/update/{postId}")
    public ResponseEntity<PostDto>updatePost(@RequestBody PostDto postDto, @PathVariable Integer postId)
    {
        PostDto UpdatedPost = postService.updatePosts(postDto, postId);
        return new ResponseEntity<>(UpdatedPost,HttpStatus.OK);
    }

    @DeleteMapping("/delete/{postId}")
    public ResponseEntity<?>deletePost(@PathVariable Integer postId)
    {
         return postService.deletePosts(postId);

    }
}
