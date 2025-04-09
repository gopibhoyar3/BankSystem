package com.BankingSystem.BankingSystem.controller;

import com.BankingSystem.BankingSystem.entity.Transaction;
import com.BankingSystem.BankingSystem.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @GetMapping
    public ResponseEntity<List<Transaction>> getAllTransactions() {
        return ResponseEntity.ok(transactionService.getAllTransactions());
    }

    @PostMapping("/deposit")
    public ResponseEntity<Transaction> deposit(@RequestParam Long accountId,
                                               @RequestParam Double amount) {
        return ResponseEntity.ok(transactionService.deposit(accountId, amount));
    }

    @PostMapping("/withdraw")
    public ResponseEntity<Transaction> withdraw(@RequestParam Long accountId,
                                                @RequestParam Double amount) {
        return ResponseEntity.ok(transactionService.withdraw(accountId, amount));
    }

    @PostMapping("/transfer")
    public ResponseEntity<Transaction> transfer(@RequestParam Long fromAccountId,
                                                @RequestParam Long toAccountId,
                                                @RequestParam Double amount) {
        return ResponseEntity.ok(transactionService.transfer(fromAccountId, toAccountId, amount));
    }

    @GetMapping("/{accountId}")
    public ResponseEntity<List<Transaction>> getTransactions(@PathVariable Long accountId) {
        return ResponseEntity.ok(transactionService.getTransactions(accountId));
    }
}