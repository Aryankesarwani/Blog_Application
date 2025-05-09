package com.example.Blog_Application.service;

import com.example.Blog_Application.DTO.CategoryDto;
import com.example.Blog_Application.entity.Category;
import org.springframework.stereotype.Service;

import java.util.List;
//@Service
public interface CategoryService {
    //create

    public CategoryDto createCategory(CategoryDto categoryDto);
    //get
    public CategoryDto getById(int cat_id);

    //update
    public CategoryDto updateCategory(CategoryDto categoryDto, int cat_id);


    // delete

    public void deleteCategory(int cat_id);
    //getall

    public List<CategoryDto> getAllCategories();

    public Category findCategoryByTitle(String title);
}
