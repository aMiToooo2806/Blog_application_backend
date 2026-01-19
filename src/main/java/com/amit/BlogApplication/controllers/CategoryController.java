package com.amit.BlogApplication.controllers;

import com.amit.BlogApplication.payloads.CategoryDto;
import com.amit.BlogApplication.services.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Tag(name = "Category APIs", description = "Category management APIs (Admin only)")
@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Operation(summary = "Create category")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Category created"),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content)
    })
    @PostMapping("/create")
    public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto) {
        CategoryDto createdCategory = this.categoryService.createCategory(categoryDto);
        return new ResponseEntity<>(createdCategory, HttpStatus.CREATED);
    }

    @Operation(summary = "Update category")
    @PutMapping("/update/{categoryId}")
    public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto categoryDto,
                                                      @Parameter(example = "1") @PathVariable Integer categoryId) {

        CategoryDto updatedCategory = this.categoryService.updateCategory(categoryDto, categoryId);
        return new ResponseEntity<>(updatedCategory, HttpStatus.OK);
    }

    @Operation(summary = "Delete category")
    @DeleteMapping("/delete/{categoryId}")
    public ResponseEntity<?> deleteCategory(@Parameter(example = "1") @PathVariable Integer categoryId) {
        this.categoryService.deleteCategory(categoryId);
        return new ResponseEntity<>(Map.of("Message", "Category Deleted Successfully"), HttpStatus.OK);
    }

    @Operation(summary = "Get category by id")
    @GetMapping("/byId/{categoryId}")
    public ResponseEntity<CategoryDto> getCategoryById(@Parameter(example = "1") @PathVariable Integer categoryId) {
        CategoryDto categoryById = this.categoryService.getCategoryById(categoryId);
        return new ResponseEntity<>(categoryById, HttpStatus.OK);
    }

    @Operation(summary = "Get all categories")
    @GetMapping("/getAll")
    public ResponseEntity<List<CategoryDto>> getAllCategory() {
        List<CategoryDto> allCategory = this.categoryService.getAllCategory();
        return new ResponseEntity<>(allCategory, HttpStatus.OK);
    }
}
