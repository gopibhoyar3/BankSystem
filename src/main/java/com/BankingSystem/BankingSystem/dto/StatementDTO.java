package com.BankingSystem.BankingSystem.dto;

import com.BankingSystem.BankingSystem.entity.Transaction;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class StatementDTO {
    private String month;
    private List<Transaction> transactions;
}