package com.example.Blog_Application.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.Getter;

@Entity
@Getter
@Data
public class Role {
    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    String role;
}

