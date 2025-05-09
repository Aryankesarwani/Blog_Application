package com.example.Blog_Application.DTO;

import com.example.Blog_Application.entity.Category;
import com.example.Blog_Application.entity.User;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatusCode;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PostDto {
    int id;
    String title;
    String content;
    String imageName;
    Date date;

    Category category;
    User user;
}
