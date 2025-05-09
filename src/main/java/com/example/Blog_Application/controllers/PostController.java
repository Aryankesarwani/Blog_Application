package com.example.Blog_Application.controllers;

import com.example.Blog_Application.DTO.PostDto;
import com.example.Blog_Application.Repo.CategoryRepo;
import com.example.Blog_Application.Repo.UserRepo;
import com.example.Blog_Application.entity.ApiResponse;
import com.example.Blog_Application.entity.Category;
import com.example.Blog_Application.entity.User;
import com.example.Blog_Application.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostController {
    @Autowired
    PostService postService;
    @Autowired
    UserRepo userRepo;
    @Autowired
    CategoryRepo categoryRepo;
    @PostMapping("/create")
    public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto,@RequestParam int user_id,@RequestParam int cat_id){
        return new ResponseEntity<>(postService.createPost(postDto,user_id,cat_id), HttpStatus.CREATED);
    }

    @PutMapping("/update/{post_Id}")
    public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto, @PathVariable int post_Id,@RequestParam int cat_id ){
        PostDto UpdatedPost = postService.updatePost(postDto,post_Id,cat_id);
        return new ResponseEntity<PostDto>(UpdatedPost,HttpStatus.OK);
    }
    @GetMapping("/getAll")
    public ResponseEntity<List<PostDto>> getAllPost(){
        return new ResponseEntity<>(postService.getAllPost(),HttpStatus.OK);
    }

    @GetMapping("/getAllByUser")
    public ResponseEntity<List<PostDto>> getAllByUser(@RequestParam String userName){
        return new ResponseEntity<>(postService.getAllByUser(userName),HttpStatus.OK);
    }

    @GetMapping("/getAllByCategory")
    public ResponseEntity<List<PostDto>> getAllByCategory(@RequestParam String categoryName){
        return new ResponseEntity<>(postService.getAllByCategory(categoryName),HttpStatus.OK);
    }
    @GetMapping("/GetByKey")
    public ResponseEntity<List<PostDto>> getAllPostByKey(@RequestParam String key){
        return new ResponseEntity<>(postService.getByTitleContaining(key),HttpStatus.OK);
    }

    @GetMapping("/getById/{post_Id}")
    public ResponseEntity<PostDto> getById(@PathVariable int post_Id){
        return new ResponseEntity<>(postService.getPostById(post_Id),HttpStatus.OK);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deletePost(@PathVariable int id){
        postService.deletePost(id);
        return new ResponseEntity<>(new ApiResponse("Post deleted Successfully",true),HttpStatus.NO_CONTENT);
    }


}
