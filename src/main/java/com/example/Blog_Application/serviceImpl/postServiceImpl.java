package com.example.Blog_Application.serviceImpl;

import com.example.Blog_Application.DTO.PostDto;
import com.example.Blog_Application.DTO.PostResponse;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
        post.setImageName("default.png");
        post.setDate(new Date());
        post.setComments(new ArrayList<>());
        post.setUser(userRepo.findById(user_id).orElseThrow(()-> new UserNotFoundException("User Not found id ="+user_id)));
        post.setCategory(categoryRepo.findById(cat_id).orElseThrow(()-> new UserNotFoundException("Category not found id = "+cat_id)));
        Post savedPost = postRepo.save(post);
        return modelMapper.map(savedPost,PostDto.class);
    }


    @Override
    public PostDto updatePost(PostDto postDto, int post_Id) {
        PostDto postDto1 = getPostById(post_Id);
        postDto1.setContent(postDto.getContent());
        postDto1.setTitle(postDto.getTitle());
        postDto1.setImageName(postDto.getImageName());
//        postDto1.setCategory(categoryRepo.findById(cat_id).orElseThrow(()-> new UserNotFoundException("Category not found id = "+cat_id)));
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
    public PostResponse getAllPost(int pageSize, int pageNumber,String sortBy) {

        Pageable pageable = PageRequest.of(pageNumber,pageSize,Sort.by(sortBy).descending());

        List<PostDto> postDtos = new ArrayList<>();
        Page<Post> pagePost = postRepo.findAll(pageable);
        List<Post> posts = pagePost.getContent();
        for(Post post : posts){
            postDtos.add(modelMapper.map(post, PostDto.class));
        }
        return PostResponse.builder()
                .content(postDtos)
                .lastPage(pagePost.isLast())
                .pageSize(pagePost.getSize())
                .pageNumber(pagePost.getNumber())
                .totalElements(pagePost.getNumberOfElements())
                .totalPages(pagePost.getTotalPages())
                .build();
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
//        List<PostDto> postDtos = new ArrayList<>();
//        for(Post post : posts) postDtos.add(modelMapper.map(post,PostDto.class));
//        return postDtos;
        return posts.stream().map((post)-> modelMapper.map(post,PostDto.class)).collect(Collectors.toList());
    }
}
