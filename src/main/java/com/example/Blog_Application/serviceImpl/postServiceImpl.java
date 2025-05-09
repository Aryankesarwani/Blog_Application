package com.example.Blog_Application.serviceImpl;

import com.example.Blog_Application.DTO.PostDto;
import com.example.Blog_Application.Exception.UserNotFoundException;
import com.example.Blog_Application.Repo.CategoryRepo;
import com.example.Blog_Application.Repo.PostRepo;
import com.example.Blog_Application.Repo.UserRepo;
import com.example.Blog_Application.entity.Category;
import com.example.Blog_Application.entity.Post;
import com.example.Blog_Application.entity.User;
import com.example.Blog_Application.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class postServiceImpl implements PostService {

    @Autowired
    ModelMapper modelMapper;
    @Autowired
    PostRepo postRepo;
    @Autowired
    UserRepo userRepo;
    @Autowired
    CategoryRepo categoryRepo;

    @Override
    public PostDto createPost(PostDto postDto,int user_id,int cat_id) {
        Post post = modelMapper.map(postDto,Post.class);
        if(postDto.getImageName() == null)post.setImageName("default.png");
        post.setDate(new Date());
        post.setUser(userRepo.findById(user_id).orElseThrow(()-> new UserNotFoundException("User Not found id ="+user_id)));
        post.setCategory(categoryRepo.findById(cat_id).orElseThrow(()-> new UserNotFoundException("Category not found id = "+cat_id)));
        Post savedPost = postRepo.save(post);
        return modelMapper.map(savedPost,PostDto.class);
    }


    @Override
    public PostDto updatePost(PostDto postDto, int post_Id,int cat_id) {
        PostDto postDto1 = getPostById(post_Id);
        postDto1.setContent(postDto.getContent());
        postDto1.setTitle(postDto.getTitle());
        postDto1.setImageName(postDto.getImageName());
//        postDto1.setUser(userRepo.findById(user_id).orElseThrow(()-> new UserNotFoundException("User Not found id ="+user_id)));
        postDto1.setCategory(categoryRepo.findById(cat_id).orElseThrow(()-> new UserNotFoundException("Category not found id = "+cat_id)));
        Post post = modelMapper.map(postDto1,Post.class);
        post.setDate(new Date());
        Post savedPost = postRepo.save(post);
        return modelMapper.map(savedPost, PostDto.class);
    }


    @Override
    public PostDto getPostById(int post_Id) {
        Optional<Post> optionalPost = postRepo.findById(post_Id);
        if(optionalPost.isEmpty()) throw new UserNotFoundException("Post not found");
        Post post = optionalPost.get();
        return modelMapper.map(post, PostDto.class);
    }

    @Override
    public List<PostDto> getAllPost() {
        List<PostDto> postDtos = new ArrayList<>();
        List<Post> posts = postRepo.findAll();
        for(Post post : posts){
            postDtos.add(modelMapper.map(post, PostDto.class));
        }
        return postDtos;
    }

    @Override
    public void deletePost(int post_Id) {
        postRepo.deleteById(post_Id);
    }

    @Override
    public List<PostDto> getAllByUser(String userName) {
        List<PostDto> postDtos = new ArrayList<>();
        User user = userRepo.findByName(userName);
        List<Post> posts = postRepo.findByUser_userId(user.getId());
        for(Post post : posts) postDtos.add(modelMapper.map(post,PostDto.class));
        return postDtos;
    }

    @Override
    public List<PostDto> getAllByCategory(String title) {
        List<PostDto> postDtos = new ArrayList<>();
        Category category = categoryRepo.findByTitle(title);
        List<Post> posts = postRepo.findByCategory_categoryId(category.getId());
        for(Post post : posts) postDtos.add(modelMapper.map(post,PostDto.class));
        return postDtos;
    }

    @Override
    public List<PostDto> getByTitleContaining(String key) {
        List<Post> posts = postRepo.findByTitleContaining(key);
        List<PostDto> postDtos = new ArrayList<>();
        for(Post post : posts) postDtos.add(modelMapper.map(post,PostDto.class));
        return postDtos;
    }
}
