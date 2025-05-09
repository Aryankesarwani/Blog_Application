package com.example.Blog_Application.controllers;

import com.example.Blog_Application.DTO.CategoryDto;
import com.example.Blog_Application.entity.ApiResponse;
import com.example.Blog_Application.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    CategoryService categoryService;
    @PostMapping("/add")
    public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto){
        CategoryDto categoryDto1 = categoryService.createCategory(categoryDto);
        return new ResponseEntity<>(categoryDto1, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto categoryDto, @PathVariable int id){
        CategoryDto updatedCategory = categoryService.updateCategory(categoryDto,id);
        return new ResponseEntity<>(updatedCategory,HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<CategoryDto> getCategoryById(@PathVariable int id){
        CategoryDto categoryDto = categoryService.getById(id);
        return new ResponseEntity<>(categoryDto,HttpStatus.ACCEPTED);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<CategoryDto>> getallCategories(){
        List<CategoryDto> categoryDtos = categoryService.getAllCategories();
        return  new ResponseEntity<>(categoryDtos,HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable int id){
        categoryService.deleteCategory(id);
        return  new ResponseEntity<>(new ApiResponse("Category deleted Successfully",true),HttpStatus.OK);
    }

}
