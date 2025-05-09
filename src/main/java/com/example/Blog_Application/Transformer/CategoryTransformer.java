package com.example.Blog_Application.Transformer;

import com.example.Blog_Application.DTO.CategoryDto;
import com.example.Blog_Application.entity.Category;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CategoryTransformer {

    @Autowired
    ModelMapper modelMapper;
    public Category DTOtoEntity(CategoryDto categoryDto){
        return modelMapper.map(categoryDto,Category.class);
    }

    public CategoryDto EntitytoDto(Category category){
        return modelMapper.map(category,CategoryDto.class);
    }
}
