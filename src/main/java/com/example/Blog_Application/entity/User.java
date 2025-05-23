package com.example.Blog_Application.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    String name;
    @Column(unique = true)
    String email;
    String password;
    String about;


    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    @JsonIgnore
    List<Post> posts = new ArrayList<>();

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    @JsonIgnore
    List<Comment> comments = new ArrayList<>();
}
