package com.example.Blog_Application.controllers;

import com.example.Blog_Application.DTO.CommentDto;
import com.example.Blog_Application.entity.ApiResponse;
import com.example.Blog_Application.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comments")
public class CommentController {

    @Autowired
    CommentService commentService;

    @PostMapping("/add")
    public ResponseEntity<CommentDto> createComment(@RequestBody CommentDto commentDto,@RequestParam Integer postId,@RequestParam Integer userId){
        return new ResponseEntity<>(commentService.createComment(commentDto,postId,userId), HttpStatus.CREATED);
    }
    @DeleteMapping("/delete/comment/{commentId}")
    public ResponseEntity<ApiResponse> deleteComment(@PathVariable Integer commentId){
        commentService.deleteComment(commentId);
        return new ResponseEntity<>(new ApiResponse("Comment deleted successfully",true),HttpStatus.OK);
    }
}
