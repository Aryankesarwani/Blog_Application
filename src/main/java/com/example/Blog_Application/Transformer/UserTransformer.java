package com.example.Blog_Application.Transformer;

import com.example.Blog_Application.entity.User;
import com.example.Blog_Application.DTO.UserDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserTransformer {
    @Autowired
    ModelMapper modelMapper ;

    public UserDto EntityToDto(User user){

        return modelMapper.map(user, UserDto.class);

    }



    public User DtoToEntity(UserDto userdto){

        return modelMapper.map(userdto,User.class);

    }
}
