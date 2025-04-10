package com.BankingSystem.BankingSystem.service;

import com.BankingSystem.BankingSystem.dto.AccountDetailsDTO;
import com.BankingSystem.BankingSystem.entity.Account;
import com.BankingSystem.BankingSystem.entity.Transaction;
import com.BankingSystem.BankingSystem.entity.User;
import com.BankingSystem.BankingSystem.repository.AccountRepository;
import com.BankingSystem.BankingSystem.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import com.BankingSystem.BankingSystem.repository.TransactionRepository;
import org.springframework.web.server.ResponseStatusException;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final UserRepository userRepository;
    private final TransactionRepository transactionRepository;

    public Account createAccount(Long userId, String type, Double balance) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Account account = new Account();
        account.setUser(user);
        account.setAccountNumber(UUID.randomUUID().toString().substring(0, 10));
        account.setAccountType(type);
        account.setBalance(balance);
        return accountRepository.save(account);
    }

    public List<Account> getAccountsByUserId(Long userId) {
        return accountRepository.findByUserId(userId);
    }

    public AccountDetailsDTO getAccountDetails(Long accountId) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        List<Transaction> transactions = transactionRepository
                .findTop5ByFromAccountIdOrToAccountIdOrderByTimestampDesc(accountId, accountId);

        return new AccountDetailsDTO(
                account.getAccountNumber(),
                account.getAccountType(),
                account.getBalance(),
                transactions
        );
    }

    public void deleteAccount(Long accountId) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found"));

        if (account.getBalance() > 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cannot delete account with non-zero balance.");
        }

        boolean hasTransactions = transactionRepository.existsByFromAccountIdOrToAccountId(accountId, accountId);
        if (hasTransactions) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cannot delete account with existing transactions.");
        }

        accountRepository.delete(account);
    }

    @Transactional
    public List<Account> calculateInterestForSavingsAccounts(double interestRate) {
        List<Account> savingsAccounts = accountRepository.findAll().stream()
                .filter(acc -> "SAVINGS".equalsIgnoreCase(acc.getAccountType()))
                .collect(Collectors.toList());

        List<Account> updatedAccounts = new ArrayList<>();

        for (Account acc : savingsAccounts) {
            double interest = acc.getBalance() * interestRate / 100;
            acc.setBalance(acc.getBalance() + interest);

            accountRepository.save(acc);

            Transaction interestTxn = new Transaction();
            interestTxn.setAmount(interest);
            interestTxn.setTransactionType("INTEREST");
            interestTxn.setToAccount(acc);
            interestTxn.setTimestamp(LocalDateTime.now());

            transactionRepository.save(interestTxn);

            updatedAccounts.add(acc);
        }

        return updatedAccounts;
    }

}
