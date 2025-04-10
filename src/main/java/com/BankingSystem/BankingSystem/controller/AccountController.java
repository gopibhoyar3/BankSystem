package com.BankingSystem.BankingSystem.controller;

import com.BankingSystem.BankingSystem.entity.Account;
import com.BankingSystem.BankingSystem.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.BankingSystem.BankingSystem.dto.AccountDetailsDTO;

import java.util.List;

@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PostMapping
    public ResponseEntity<Account> createAccount(@RequestParam Long userId,
                                                 @RequestParam String type,
                                                 @RequestParam Double balance) {
        Account account = accountService.createAccount(userId, type, balance);
        return ResponseEntity.ok(account);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<Account>> getAccounts(@PathVariable Long userId) {
        return ResponseEntity.ok(accountService.getAccountsByUserId(userId));
    }

    @GetMapping("/details/{accountId}")
    public ResponseEntity<AccountDetailsDTO> getAccountDetails(@PathVariable Long accountId) {
        return ResponseEntity.ok(accountService.getAccountDetails(accountId));
    }

    @DeleteMapping("/{accountId}")
    public ResponseEntity<String> deleteAccount(@PathVariable Long accountId) {
        accountService.deleteAccount(accountId);
        return ResponseEntity.ok("Account deleted successfully.");
    }

    @PostMapping("/calculate-interest")
    public ResponseEntity<List<Account>> applyInterest(@RequestParam(defaultValue = "3.0") double rate) {
        List<Account> updated = accountService.calculateInterestForSavingsAccounts(rate);
        return ResponseEntity.ok(updated);
    }

}
