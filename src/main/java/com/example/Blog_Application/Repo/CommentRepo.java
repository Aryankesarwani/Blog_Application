package com.example.Blog_Application.Repo;

import com.example.Blog_Application.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepo extends JpaRepository<Comment,Integer> {

}
