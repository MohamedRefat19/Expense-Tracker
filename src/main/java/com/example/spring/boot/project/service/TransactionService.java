package com.example.spring.boot.project.service;

import com.example.spring.boot.project.DTO.TransactionDTO;
import com.example.spring.boot.project.Repository.TransactionRepository;
import com.example.spring.boot.project.model.Category;
import com.example.spring.boot.project.model.Transaction;
import com.example.spring.boot.project.model.TransactionType;
import com.example.spring.boot.project.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public class TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private UserService userService;

    public List<Transaction> getTransactionsByUsername(String username) {
        User user = userService.findUserBYUsername(username);
        return transactionRepository.findByUserId(user.getId());
    }

    public Transaction addTransaction(TransactionDTO transaction, String username) {
        validateTransactionDTO(transaction) ;
        User user = userService.findUserBYUsername(username);
        Transaction newtransaction = new Transaction();
        newtransaction.setUser(user);
        newtransaction.setTransactionType(new TransactionType(transaction.getTransactionType()));
        newtransaction.setCategory(new Category(transaction.getCategory()));
        newtransaction.setDate(transaction.getDate());
        newtransaction.setAmount(transaction.getAmount());
        return transactionRepository.save(newtransaction);
    }

    public Transaction updateTransaction(Long transactionId, TransactionDTO transactionDTO, String username) {
        validateTransactionDTO(transactionDTO);
        Transaction transaction = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new RuntimeException("Transaction not found with ID: " + transactionId));
        User user = userService.findUserBYUsername(username);

        transaction.setUser(user);
        transaction.setTransactionType(new TransactionType(transactionDTO.getTransactionType()));
        transaction.setCategory(new Category(transactionDTO.getCategory()));
        transaction.setAmount(transactionDTO.getAmount());
        transaction.setDate(transactionDTO.getDate());
        return transactionRepository.save(transaction);
    }

    public void deleteTransactionsByUsername(String username) {
        User user = userService.findUserBYUsername(username);
        List<Transaction> transactions = transactionRepository.findByUserId(user.getId());
        transactionRepository.deleteAll(transactions);
    }

    public void deleteSpecificTransaction(Long transactionId, String username) {
        Transaction transaction = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new RuntimeException("Transaction not found with ID: " + transactionId));
        User user = userService.findUserBYUsername(username);

        if (!transaction.getUser().equals(user)) {
            throw new RuntimeException("Transaction does not belong to the user: " + username);
        }
        transactionRepository.delete(transaction);
    }
    private void validateTransactionDTO(TransactionDTO transaction) {
        if (transaction == null) {
            throw new RuntimeException("Transaction data cannot be null");
        }
        if (transaction.getTransactionType() == null || transaction.getTransactionType().trim().isEmpty()) {
            throw new RuntimeException("Transaction type is required");
        }
        if (transaction.getCategory() == null || transaction.getCategory().trim().isEmpty()) {
            throw new RuntimeException("Category is required");
        }
        if (transaction.getDate() == null) {
            throw new RuntimeException("Transaction date is required");
        }
        if ( transaction.getAmount() <= 0) {
            throw new RuntimeException("Transaction amount must be greater than 0");
        }
    }


}
