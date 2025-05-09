package com.example.Blog_Application.service;

import com.example.Blog_Application.DTO.PostDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PostService {
    public PostDto createPost(PostDto postDto,int user_id, int cat_id);
    public PostDto updatePost(PostDto postDto,int post_Id,int cat_id);
    public PostDto getPostById(int post_Id);
    public List<PostDto> getAllPost();
    public void deletePost(int post_Id);

    List<PostDto> getAllByUser(String userName);

    List<PostDto> getAllByCategory(String categoryName);

    List<PostDto> getByTitleContaining(String key);
}
