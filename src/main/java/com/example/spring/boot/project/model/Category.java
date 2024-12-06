package com.example.spring.boot.project.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Table
@Entity
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    public Category() {}

    public Category(String name) {
        this.name = name;
    }
}
