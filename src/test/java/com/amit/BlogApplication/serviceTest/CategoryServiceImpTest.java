package com.amit.BlogApplication.serviceTest;

import com.amit.BlogApplication.entities.Category;
import com.amit.BlogApplication.payloads.CategoryDto;
import com.amit.BlogApplication.repositories.CategoryRepo;
import com.amit.BlogApplication.services.Impl.CategoryServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceImpTest {
    @Mock
    private CategoryRepo categoryRepo;

    @Spy // Use Spy or a real instance instead of Mock
    private ModelMapper modelMapper = new ModelMapper();

    @InjectMocks
    private CategoryServiceImpl categoryService;

    @Test
    void createCategory_shouldPersistAndMapCorrectly() {
        // Arrange
        CategoryDto inputDto = new CategoryDto();
        inputDto.setCategoryTitle("Electronics");

        Category savedEntity = new Category();
        savedEntity.setCategoryId(1);
        savedEntity.setCategoryTitle("Electronics");

        // We only mock the DB, not the Mapper
        when(categoryRepo.save(any(Category.class))).thenReturn(savedEntity);

        // Act
        CategoryDto result = categoryService.createCategory(inputDto);

        // Assert
        assertEquals(1, result.getCategoryId());
        assertEquals("Electronics", result.getCategoryTitle());
        // If ModelMapper fails to map names, this assertion fails (which is GOOD)
    }

}
