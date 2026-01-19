package com.amit.BlogApplication.payloads;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class PostDto {

    @Schema(example = "1")
    private Integer postId;

    @Schema(example = "Spring Boot Security")
    private String postTitle;

    @Schema(example = "This post explains JWT authentication in Spring Boot...")
    private String postContent;

    @Schema(example = "post.png")
    private String imageName;

    private Date addedDate;

    private CategoryDto category;

    private UserDto users;

    public PostDto() {
    }

}
