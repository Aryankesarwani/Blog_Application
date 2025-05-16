package com.example.Blog_Application.Repo;

import com.example.Blog_Application.entity.Post;
//import org.springframework.data.domain.Page;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

//import java.awt.print.Pageable;
import java.util.List;

@Repository
public interface PostRepo extends JpaRepository<Post,Integer> {
    @Query("SELECT p from Post  p where p.user.id = ?1")
    List<Post> findByUser_userId(int id);

    Page<Post> findAll(Pageable pageable);

    @Query("SELECT p FROM Post p WHERE p.category.id = ?1")
    List<Post> findByCategory_categoryId(int id);

//    @Query("SELECT p FROM Post p WHERE p.title LIKE %?1%")
    List<Post> findByTitleContaining(String title);

}
