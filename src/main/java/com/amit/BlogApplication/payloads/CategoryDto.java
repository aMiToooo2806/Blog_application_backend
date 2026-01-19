package com.amit.BlogApplication.payloads;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CategoryDto {

    @Schema(example = "1")
    private Integer categoryId;

    @NotEmpty
    @Schema(example = "Technology")
    private String categoryTitle;

    @NotEmpty
    @Schema(example = "All technology related posts")
    private String categoryDescription;

    public CategoryDto() {
    }

}
