package com.example.spring.boot.project.DTO;

import lombok.Data;

import java.time.LocalDate;
@Data
public class TransactionDTO {
    private String transactionType ;
    private String category ;
    private LocalDate date ;
    private double amount ;
}
