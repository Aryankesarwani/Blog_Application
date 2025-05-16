package com.example.Blog_Application.serviceImpl;

import com.example.Blog_Application.DTO.CommentDto;
import com.example.Blog_Application.Exception.UserNotFoundException;
import com.example.Blog_Application.Repo.CommentRepo;
import com.example.Blog_Application.Repo.PostRepo;
import com.example.Blog_Application.Repo.UserRepo;
import com.example.Blog_Application.entity.Comment;
import com.example.Blog_Application.entity.Post;
import com.example.Blog_Application.entity.User;
import com.example.Blog_Application.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    CommentRepo commentRepo;
    @Autowired
    PostRepo postRepo;
    @Autowired
    UserRepo userRepo;
    @Autowired
    ModelMapper modelMapper;


    @Override
    public CommentDto createComment(CommentDto commentDto,Integer postId,Integer userId) {
        Post post = postRepo.findById(postId).orElseThrow(()-> new UserNotFoundException("post not found ID:"+postId));
        User user = userRepo.findById(userId).orElseThrow(()-> new UserNotFoundException("user not found ID:"+userId));
        Comment comment = modelMapper.map(commentDto,Comment.class);
        comment.setPost(post);
        comment.setUser(user);
        post.getComments().add(comment);
        postRepo.save(post);
        int n = post.getComments().size();
        Comment savedComment = post.getComments().get(n-1);
        return modelMapper.map(savedComment,CommentDto.class);



    }

    @Override
    public void deleteComment(Integer commentId) {
        Comment comment = commentRepo.findById(commentId).orElseThrow(()-> new UserNotFoundException("Comment not exist Id: "+commentId));
        Post post = comment.getPost();
        post.getComments().remove(comment);
        commentRepo.delete(comment);
        postRepo.save(post);
    }
}
