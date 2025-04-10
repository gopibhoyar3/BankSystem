package com.BankingSystem.BankingSystem.service;

import com.BankingSystem.BankingSystem.entity.Account;
import com.BankingSystem.BankingSystem.entity.Loan;
import com.BankingSystem.BankingSystem.entity.Transaction;
import com.BankingSystem.BankingSystem.repository.AccountRepository;
import com.BankingSystem.BankingSystem.repository.LoanRepository;
import com.BankingSystem.BankingSystem.repository.TransactionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class LoanService {

    private final LoanRepository loanRepository;
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    @Transactional
    public Loan requestLoan(Long accountId, Double amount, Double interestRate) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        Loan loan = new Loan();
        loan.setAccount(account);
        loan.setAmount(amount);
        loan.setRemainingAmount(amount + (amount * interestRate / 100));
        loan.setInterestRate(interestRate);
        loan.setIssuedDate(LocalDateTime.now());

        account.setBalance(account.getBalance() + amount);
        accountRepository.save(account);

        // Create a deposit transaction for the loan
        Transaction txn = new Transaction();
        txn.setAmount(amount);
        txn.setTransactionType("LOAN_CREDIT");
        txn.setToAccount(account);
        txn.setTimestamp(LocalDateTime.now());
        transactionRepository.save(txn);

        return loanRepository.save(loan);
    }

    @Transactional
    public Loan repayLoan(Long loanId, Double repayAmount) {
        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new RuntimeException("Loan not found"));

        Account account = loan.getAccount();

        if (account.getBalance() < repayAmount)
            throw new RuntimeException("Insufficient balance to repay loan");

        account.setBalance(account.getBalance() - repayAmount);
        loan.setRemainingAmount(loan.getRemainingAmount() - repayAmount);

        if (loan.getRemainingAmount() <= 0) {
            loan.setIsPaid(true);
        }

        accountRepository.save(account);
        loanRepository.save(loan);

        // Log repayment transaction
        Transaction txn = new Transaction();
        txn.setAmount(repayAmount);
        txn.setTransactionType("LOAN_REPAY");
        txn.setFromAccount(account);
        txn.setTimestamp(LocalDateTime.now());
        transactionRepository.save(txn);

        return loan;
    }
}