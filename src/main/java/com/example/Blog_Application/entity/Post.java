package com.example.Blog_Application.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.*;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "posts")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;


    @NotBlank
    String title;
    String content;
    String imageName;
    Date date;

    @ManyToOne
    @JoinColumn(name = "category_id")
    Category category;


    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;

    @OneToMany(mappedBy = "post",cascade = CascadeType.ALL)
    @JsonIgnore
    List<Comment> comments = new ArrayList<>();
}
