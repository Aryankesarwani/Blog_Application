package com.example.Blog_Application.service;

import com.example.Blog_Application.Exception.UserNotFoundException;
import com.example.Blog_Application.DTO.UserDto;
import com.example.Blog_Application.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    UserDto createUser(UserDto user);
    UserDto updateUser(UserDto userDto,Integer user_ID) throws UserNotFoundException;
    UserDto getuserById(Integer user_Id) throws UserNotFoundException;
    List<UserDto> getAllUsers();
    void deleteUser(Integer user_Id) throws UserNotFoundException;

    User getuserByName(String userName);

    String varify(UserDto userDto);
}
