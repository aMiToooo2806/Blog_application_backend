package com.amit.BlogApplication.payloads;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@Schema(name = "PostResponse", description = "Paginated response for posts")
public class PostResponse {

    @Schema(description = "List of posts for current page")
    private List<PostDto> content;

    @Schema(description = "Current page number (0-based)", example = "0")
    private int pageNumber;

    @Schema(description = "Number of items per page", example = "4")
    private int pageSize;

    @Schema(description = "Total number of posts available", example = "100")
    private Long totalElements;

    @Schema(description = "Total number of pages available", example = "25")
    private int totalPages;

    @Schema(description = "Is this the last page?", example = "false")
    private boolean lastPage;

    public PostResponse() {
    }
}
