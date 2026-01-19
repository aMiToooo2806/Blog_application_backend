package com.amit.BlogApplication.controllers;

import com.amit.BlogApplication.payloads.PostDto;
import com.amit.BlogApplication.payloads.PostResponse;
import com.amit.BlogApplication.services.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Post APIs", description = "Operations related to posts")
@SecurityRequirement(name = "bearerAuth")
@Slf4j
@RestController
@RequestMapping("/api")
public class PostController {

    @Autowired
    private PostService postService;

    @Operation(summary = "Create Post",
            description = "Admin can create for any user. User can create post only for self.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Post created",
                    content = @Content(schema = @Schema(implementation = PostDto.class))),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content)
    })
    @PreAuthorize("hasRole('ADMIN') OR @userSecurity.isOwner(#userId)")
    @PostMapping("/user/{userId}/category/{categoryId}/posts")
    public ResponseEntity<PostDto> createPost(
            @RequestBody PostDto postDto,
            @Parameter(example = "1") @PathVariable Integer userId,
            @Parameter(example = "1") @PathVariable Integer categoryId) {

        PostDto createdPost = this.postService.createPosts(postDto, userId, categoryId);
        return new ResponseEntity<>(createdPost, HttpStatus.CREATED);
    }

    @Operation(summary = "Get posts by user",
            description = "Admin can view any user's posts. User can view only own posts.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Posts returned"),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content)
    })
    @PreAuthorize("hasRole('ADMIN') OR @userSecurity.isOwner(#userId)")
    @GetMapping("/user/{userId}/posts")
    public ResponseEntity<List<PostDto>> getPostsByUser(@Parameter(example = "1") @PathVariable Integer userId) {
        List<PostDto> postByUser = this.postService.getPostByUser(userId);
        return new ResponseEntity<>(postByUser, HttpStatus.OK);
    }

    @Operation(summary = "Get posts by category",
            description = "Any authenticated user can view posts by category.")
    @ApiResponse(responseCode = "200", description = "Posts returned")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping("/category/{categoryId}/posts")
    public ResponseEntity<List<PostDto>> getPostsByCategory(@Parameter(example = "1") @PathVariable Integer categoryId) {
        List<PostDto> postByCategory = this.postService.getPostByCategory(categoryId);
        return new ResponseEntity<>(postByCategory, HttpStatus.OK);
    }

    @Operation(summary = "Get all posts (paginated)",
            description = "Returns paginated list of posts.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Posts returned",
                    content = @Content(schema = @Schema(implementation = PostResponse.class))),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content)
    })
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/AllPosts")
    public ResponseEntity<PostResponse> getAllPosts(
            @Parameter(description = "Page number (0-based)", example = "0")
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
            @Parameter(description = "Page size", example = "4")
            @RequestParam(value = "pageSize", defaultValue = "4", required = false) Integer pageSize) {

        PostResponse allPosts = this.postService.getAllPosts(pageNumber, pageSize);
        return new ResponseEntity<>(allPosts, HttpStatus.OK);
    }

    @Operation(summary = "Get post by id", description = "Any authenticated user can view post details.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Post returned"),
            @ApiResponse(responseCode = "404", description = "Post not found", content = @Content)
    })
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping("/post/{postId}")
    public ResponseEntity<PostDto> getPostByPostId(@Parameter(example = "1") @PathVariable Integer postId) {
        PostDto postById = this.postService.getPostById(postId);
        return new ResponseEntity<>(postById, HttpStatus.OK);
    }

    @Operation(summary = "Update post", description = "Admin or post owner can update.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Post updated"),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content)
    })
    @PreAuthorize("hasRole('ADMIN') OR @postSecurity.isPostOwner(#postId)")
    @PutMapping("/update/{postId}")
    public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto,
                                              @Parameter(example = "1") @PathVariable Integer postId) {

        PostDto updatedPost = postService.updatePosts(postDto, postId);
        return new ResponseEntity<>(updatedPost, HttpStatus.OK);
    }

    @Operation(summary = "Delete post", description = "Admin or post owner can delete.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Post deleted"),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content)
    })
    @PreAuthorize("hasRole('ADMIN') OR @postSecurity.isPostOwner(#postId)")
    @DeleteMapping("/delete/{postId}")
    public ResponseEntity<?> deletePost(@Parameter(example = "1") @PathVariable Integer postId) {
        return postService.deletePosts(postId);
    }
}
