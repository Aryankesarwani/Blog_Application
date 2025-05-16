package com.example.Blog_Application.service;

import com.example.Blog_Application.DTO.CommentDto;

public interface CommentService {
    CommentDto createComment(CommentDto commentDto,Integer postId,Integer userId);
    void deleteComment(Integer commentId);
}
