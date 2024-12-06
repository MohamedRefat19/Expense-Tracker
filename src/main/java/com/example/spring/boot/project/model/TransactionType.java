package com.example.spring.boot.project.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table
public class TransactionType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String type;

    public TransactionType() {
    }

    public TransactionType(String type) {
        this.type = type;
    }
}