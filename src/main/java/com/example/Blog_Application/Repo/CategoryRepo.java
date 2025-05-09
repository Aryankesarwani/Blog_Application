package com.example.Blog_Application.Repo;

import com.example.Blog_Application.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepo extends JpaRepository<Category,Integer> {

//    public Category findByName(String Name);
//    @Query("SELECT c FROM Category c where c.title = ?1")
    Category findByTitle(String title);
}
