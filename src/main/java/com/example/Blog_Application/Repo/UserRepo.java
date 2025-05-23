package com.example.Blog_Application.Repo;

import com.example.Blog_Application.entity.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User,Integer> {
    public User findByName(String name);
    User findByEmail(@Email(message = "Email is not Valid!!") @NotBlank(message = "Email can not be Blank") String email);
}
