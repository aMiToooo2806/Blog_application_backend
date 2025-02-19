package com.amit.BlogApplication.controllers;

import com.amit.BlogApplication.payloads.CategoryDto;
import com.amit.BlogApplication.services.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping("/create")
    public ResponseEntity<CategoryDto>createCategory(@Valid @RequestBody CategoryDto categoryDto)
    {
        CategoryDto createdCategory = this.categoryService.createCategory(categoryDto);
        return new ResponseEntity<>(createdCategory, HttpStatus.CREATED);
    }

    @PutMapping("/update/{categoryId}")
    public ResponseEntity<CategoryDto>updateCategory(@Valid @RequestBody CategoryDto categoryDto, @PathVariable Integer categoryId)
    {
        CategoryDto UpdatedCategory = this.categoryService.updateCategory(categoryDto, categoryId);
        return new ResponseEntity<>(UpdatedCategory,HttpStatus.OK);
    }

    @DeleteMapping("/delete/{categoryId}")
    public ResponseEntity<?>deleteCategory(@PathVariable Integer categoryId)
    {
        this.categoryService.deleteCategory(categoryId);
        return new ResponseEntity<>(Map.of("Message","Category Deleted Successfully"),HttpStatus.OK);
    }

    @GetMapping("/byId/{categoryId}")
    public ResponseEntity<CategoryDto>getCategoryById(@PathVariable Integer categoryId)
    {
        CategoryDto categoryById = this.categoryService.getCategoryById(categoryId);
        return new ResponseEntity<>(categoryById,HttpStatus.OK);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<CategoryDto>>getAllCategory()
    {
        List<CategoryDto> allCategory = this.categoryService.getAllCategory();
        return new ResponseEntity<>(allCategory,HttpStatus.OK);
    }

}
