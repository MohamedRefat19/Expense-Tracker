package com.example.spring.boot.project.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "transaction_type_id", nullable = false)
    private TransactionType transactionType;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    private Double amount;
    private LocalDate date;



}
