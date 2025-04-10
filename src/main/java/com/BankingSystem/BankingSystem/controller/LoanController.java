package com.BankingSystem.BankingSystem.controller;

import com.BankingSystem.BankingSystem.entity.Loan;
import com.BankingSystem.BankingSystem.service.LoanService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/loans")
@RequiredArgsConstructor
public class LoanController {

    private final LoanService loanService;

    @PostMapping("/request")
    public ResponseEntity<Loan> requestLoan(@RequestParam Long accountId,
                                            @RequestParam Double amount,
                                            @RequestParam(defaultValue = "5.0") Double interestRate) {
        return ResponseEntity.ok(loanService.requestLoan(accountId, amount, interestRate));
    }

    @PostMapping("/repay")
    public ResponseEntity<Loan> repayLoan(@RequestParam Long loanId,
                                          @RequestParam Double amount) {
        return ResponseEntity.ok(loanService.repayLoan(loanId, amount));
    }
}