package com.example.Blog_Application.controllers;

import com.example.Blog_Application.DTO.ImageResponse;
import com.example.Blog_Application.DTO.PostDto;
import com.example.Blog_Application.DTO.PostResponse;
import com.example.Blog_Application.Repo.CategoryRepo;
import com.example.Blog_Application.Repo.UserRepo;
import com.example.Blog_Application.config.AppConstants;
import com.example.Blog_Application.entity.ApiResponse;
import com.example.Blog_Application.entity.Post;
import com.example.Blog_Application.service.FileService;
import com.example.Blog_Application.service.PostService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/posts")
public class PostController {
    @Autowired
    PostService postService;
    @Autowired
    FileService fileService;

    @Value("${project.image}")//same as in properties file
    private String path;


    @Autowired
    CategoryRepo categoryRepo;
    @PostMapping("/create")
    public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto,@RequestParam int user_id,@RequestParam int cat_id){
        return new ResponseEntity<>(postService.createPost(postDto,user_id,cat_id), HttpStatus.CREATED);
    }

    @PutMapping("/update/{post_Id}")
    public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto, @PathVariable int post_Id){
        PostDto UpdatedPost = postService.updatePost(postDto,post_Id);
        return new ResponseEntity<PostDto>(UpdatedPost, OK);
    }
    @GetMapping("/getAll")
    public ResponseEntity<PostResponse> getAllPost(@RequestParam(value = "pageSize",defaultValue = AppConstants.PAGE_SIZE,required = false)Integer pageSize,
                                                   @RequestParam(value = "pageNumber",defaultValue = AppConstants.PAGE_NUMBER,required = false) Integer pageNumber,
                                                   @RequestParam(value = "sortBy",defaultValue = AppConstants.SORT_BY,required = false) String sortBy){
        return new ResponseEntity<>(postService.getAllPost(pageSize,pageNumber,sortBy), OK);
    }

    @GetMapping("/getAllByUser")
    public ResponseEntity<List<PostDto>> getAllByUser(@RequestParam String userName){
        return new ResponseEntity<>(postService.getAllByUser(userName), OK);
    }

    @GetMapping("/getAllByCategory")
    public ResponseEntity<List<PostDto>> getAllByCategory(@RequestParam String categoryName){
        return new ResponseEntity<>(postService.getAllByCategory(categoryName), OK);
    }
    @GetMapping("/GetByKey")
    public ResponseEntity<List<PostDto>> getAllPostByKey(@RequestParam(value = "key",required = false) String key){
        return new ResponseEntity<>(postService.getByTitleContaining(key), OK);
    }

    @GetMapping("/getById/{post_Id}")
    public ResponseEntity<PostDto> getById(@PathVariable int post_Id){
        return new ResponseEntity<>(postService.getPostById(post_Id), OK);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deletePost(@PathVariable int id){
        postService.deletePost(id);
        return new ResponseEntity<>(new ApiResponse("Post deleted Successfully",true),HttpStatus.NO_CONTENT);
    }




    // post image upload
    @PostMapping("/post/upload/image/{postId}")
    public ResponseEntity<PostDto> uploadPostImage(@PathVariable("postId") Integer post_id ,@RequestParam("image")MultipartFile image) throws IOException {

        System.out.println("hellp");

            PostDto postDto = postService.getPostById(post_id);
            String fileName = fileService.uploadImage(path,image);
            postDto.setImageName(fileName);
            PostDto updatePost = postService.updatePost(postDto,post_id);
            return new ResponseEntity<PostDto>(updatePost, HttpStatus.OK);
    }


    // method to serve files
    @GetMapping(value = "/post/image/{imageName}",produces = MediaType.IMAGE_JPEG_VALUE)
    public void downloadImage(@PathVariable("imageName")String imageName,
                              HttpServletResponse response) throws IOException{
        InputStream resource = fileService.getResource(path,imageName);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource,response.getOutputStream());
    }

}
