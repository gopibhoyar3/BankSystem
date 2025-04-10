package com.BankingSystem.BankingSystem.dto;

import com.BankingSystem.BankingSystem.entity.Transaction;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class AccountDetailsDTO {
    private String accountNumber;
    private String accountType;
    private Double balance;
    private List<Transaction> recentTransactions;
}

