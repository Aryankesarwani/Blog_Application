package com.example.Blog_Application.controllers;

import com.example.Blog_Application.Exception.UserNotFoundException;
import com.example.Blog_Application.service.UserService;
import com.example.Blog_Application.DTO.UserDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto){
        return new ResponseEntity<>(userService.createUser(userDto), HttpStatus.CREATED);
    }
    @PostMapping("/login")
    public String login(@RequestBody UserDto userDto){
        System.out.println("assess of login");
        return userService.varify(userDto);
    }
    @PutMapping("/update/{user_id}")
    public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto,@PathVariable int user_id){
        return new ResponseEntity<>(userService.updateUser(userDto,user_id), HttpStatus.ACCEPTED);
    }
    @GetMapping("/get")
    public ResponseEntity<UserDto> getUserById(@RequestParam int id){
        return  new ResponseEntity<>(userService.getuserById(id),HttpStatus.ACCEPTED);
    }
    @GetMapping("/getAll")
    public ResponseEntity<List<UserDto>> getAllUser(){
        return new ResponseEntity<>(userService.getAllUsers(),HttpStatus.ACCEPTED);
    }
    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteUser(@RequestParam int id){
        try {
            userService.deleteUser(id);
        } catch (UserNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("User deleted Successfully",HttpStatus.ACCEPTED);
    }
}
