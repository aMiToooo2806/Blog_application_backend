package com.amit.BlogApplication.services;

import com.amit.BlogApplication.payloads.CategoryDto;

import java.util.List;

public interface CategoryService {

    public CategoryDto createCategory(CategoryDto categoryDto);

    public CategoryDto updateCategory(CategoryDto categoryDto,Integer categoryId);

    public void deleteCategory(Integer categoryId);

    public CategoryDto getCategoryById(Integer categoryId);

    public List<CategoryDto>getAllCategory();


}
