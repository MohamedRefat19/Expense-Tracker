package com.example.spring.boot.project.controller;

import com.example.spring.boot.project.DTO.TransactionDTO;
import com.example.spring.boot.project.model.Transaction;
import com.example.spring.boot.project.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transactions")
public class TransactionController {
    @Autowired
    private TransactionService transactionService;

    @GetMapping("/get/{username}")
    public ResponseEntity<List<Transaction>> getTransactionsByUsername(@PathVariable String username) {
        List<Transaction> transactions = transactionService.getTransactionsByUsername(username);
        return ResponseEntity.ok(transactions);
    }
    @PostMapping("/add/{username}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Transaction> addTransaction(
            @PathVariable String username,
            @RequestBody TransactionDTO transactionDTO) {
        Transaction transaction = transactionService.addTransaction(transactionDTO, username);
        return ResponseEntity.ok(transaction);
    }
    @PutMapping("/update/{transactionId}/{username}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Transaction> updateTransaction(
            @PathVariable Long transactionId,
            @PathVariable String username,
            @RequestBody TransactionDTO transactionDTO) {
        Transaction transaction = transactionService.updateTransaction(transactionId, transactionDTO, username);
        return ResponseEntity.ok(transaction);
    }
    @DeleteMapping("/delete/user/{username}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<String> deleteTransactionsByUsername(@PathVariable String username) {
        transactionService.deleteTransactionsByUsername(username);
        return ResponseEntity.ok("All transactions for user " + username + " have been deleted.");
    }
    @DeleteMapping("/delete/{transactionId}/{username}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<String> deleteSpecificTransaction(
            @PathVariable Long transactionId,
            @PathVariable String username) {
        transactionService.deleteSpecificTransaction(transactionId, username);
        return ResponseEntity.ok("Transaction " + transactionId + " for user " + username + " has been deleted.");
    }



}
