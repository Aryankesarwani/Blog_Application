package com.example.Blog_Application.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    String content;

    @ManyToOne
    @JoinColumn
    User user;

    @ManyToOne
    @JoinColumn
    Post post;

}
