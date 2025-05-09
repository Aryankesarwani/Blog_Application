package com.example.Blog_Application.serviceImpl;

import com.example.Blog_Application.DTO.CategoryDto;
import com.example.Blog_Application.Exception.UserNotFoundException;
import com.example.Blog_Application.Repo.CategoryRepo;
import com.example.Blog_Application.Transformer.CategoryTransformer;
import com.example.Blog_Application.entity.Category;
import com.example.Blog_Application.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryRepo categoryRepo;
    @Autowired
    CategoryTransformer categoryTransformer;
    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        Category Savedcategory = categoryRepo.save(categoryTransformer.DTOtoEntity(categoryDto));
        return categoryTransformer.EntitytoDto(Savedcategory);
    }

    @Override
    public CategoryDto getById(int cat_id) {
        Optional<Category> category = categoryRepo.findById(cat_id);
        if(category.isEmpty()) throw new UserNotFoundException("Category not found id = "+cat_id);
        return categoryTransformer.EntitytoDto(category.get());

    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, int cat_id) {
        CategoryDto currentCategory = getById(cat_id);
        currentCategory.setTitle(categoryDto.getTitle());
        currentCategory.setDescription(categoryDto.getDescription());
        return createCategory(currentCategory);
    }

    @Override
    public void deleteCategory(int cat_id) {

        categoryRepo.deleteById(getById(cat_id).getId());
    }

    @Override
    public List<CategoryDto> getAllCategories() {

        List<Category> categories = categoryRepo.findAll();
        List<CategoryDto> categoryDtos = new ArrayList<>();
        for (Category category : categories){
            categoryDtos.add(categoryTransformer.EntitytoDto(category));
        }

        return categoryDtos;
    }

    @Override
    public Category findCategoryByTitle(String title) {
        return categoryRepo.findByTitle(title);

    }
}
