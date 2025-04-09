package com.BankingSystem.BankingSystem.controller;

import com.BankingSystem.BankingSystem.entity.Account;
import com.BankingSystem.BankingSystem.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
}
