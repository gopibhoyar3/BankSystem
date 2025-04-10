package com.BankingSystem.BankingSystem.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double amount;

    private Double remainingAmount;

    private Double interestRate;

    private LocalDateTime issuedDate;

    private Boolean isPaid = false;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;
}
