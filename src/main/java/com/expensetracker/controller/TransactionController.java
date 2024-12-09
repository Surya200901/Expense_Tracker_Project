package com.expensetracker.controller;

import com.expensetracker.dto.TransactionRequest;
import com.expensetracker.dto.TransactionResponse;
import com.expensetracker.entity.AppUser;
import com.expensetracker.entity.Transaction;
import com.expensetracker.repository.TransactionRepository;
import com.expensetracker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.transaction.Transactional;
import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private UserRepository userRepository;

    // Create a transaction
    @PostMapping
    public ResponseEntity<TransactionResponse> createTransaction(@RequestBody TransactionRequest transactionRequest) {
        AppUser user = userRepository.findByUsername(transactionRequest.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Transaction transaction = new Transaction(transactionRequest.getDescription(), transactionRequest.getAmount(), transactionRequest.getDate(), user);
        transactionRepository.save(transaction);

        return ResponseEntity.ok(new TransactionResponse(transaction.getId(), "Transaction created successfully"));
    }

    // Get all transactions for a user
    @GetMapping("/user/{username}")
    public List<Transaction> getTransactionsByUser(@PathVariable String username) {
        return transactionRepository.findByUser_Username(username);
    }

    // Get transaction by ID
    @GetMapping("/{id}")
    public ResponseEntity<Transaction> getTransactionById(@PathVariable Long id) {
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transaction not found"));
        return ResponseEntity.ok(transaction);
    }

    // Update a transaction
    @PutMapping("/{id}")
    public ResponseEntity<Transaction> updateTransaction(@PathVariable Long id, @RequestBody TransactionRequest transactionRequest) {
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transaction not found"));

        transaction.setDescription(transactionRequest.getDescription());
        transaction.setAmount(transactionRequest.getAmount());
        transaction.setDate(transactionRequest.getDate());

        transactionRepository.save(transaction);

        return ResponseEntity.ok(transaction);
    }

    // Delete a transaction
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTransaction(@PathVariable Long id) {
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transaction not found"));
        
        transactionRepository.delete(transaction);
        
        return ResponseEntity.ok("Transaction deleted successfully");
    }

    // Analytics: Total amount spent by a user
    @GetMapping("/analytics/{username}")
    public ResponseEntity<Double> getTotalSpent(@PathVariable String username) {
        List<Transaction> transactions = transactionRepository.findByUser_Username(username);
        double totalSpent = transactions.stream().mapToDouble(Transaction::getAmount).sum();
        return ResponseEntity.ok(totalSpent);
    }
}
