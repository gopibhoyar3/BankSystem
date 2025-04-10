package com.BankingSystem.BankingSystem.service;

import com.BankingSystem.BankingSystem.entity.Account;
import com.BankingSystem.BankingSystem.entity.Transaction;
import com.BankingSystem.BankingSystem.repository.AccountRepository;
import com.BankingSystem.BankingSystem.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.LinkedHashMap;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    public Transaction deposit(Long accountId, Double amount) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        account.setBalance(account.getBalance() + amount);
        accountRepository.save(account);

        Transaction txn = new Transaction();
        txn.setAmount(amount);
        txn.setTransactionType("DEPOSIT");
        txn.setToAccount(account);

        return transactionRepository.save(txn);
    }

    public Transaction withdraw(Long accountId, Double amount) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        if (account.getBalance() < amount)
            throw new RuntimeException("Insufficient funds");

        account.setBalance(account.getBalance() - amount);
        accountRepository.save(account);

        Transaction txn = new Transaction();
        txn.setAmount(amount);
        txn.setTransactionType("WITHDRAW");
        txn.setFromAccount(account);

        return transactionRepository.save(txn);
    }

    public Transaction transfer(Long fromAccountId, Long toAccountId, Double amount) {
        Account from = accountRepository.findById(fromAccountId)
                .orElseThrow(() -> new RuntimeException("From account not found"));

        Account to = accountRepository.findById(toAccountId)
                .orElseThrow(() -> new RuntimeException("To account not found"));

        if (from.getBalance() < amount)
            throw new RuntimeException("Insufficient funds");

        from.setBalance(from.getBalance() - amount);
        to.setBalance(to.getBalance() + amount);

        accountRepository.save(from);
        accountRepository.save(to);

        Transaction txn = new Transaction();
        txn.setAmount(amount);
        txn.setTransactionType("TRANSFER");
        txn.setFromAccount(from);
        txn.setToAccount(to);

        return transactionRepository.save(txn);
    }

    public List<Transaction> getTransactions(Long accountId) {
        return transactionRepository.findByFromAccountIdOrToAccountId(accountId, accountId);
    }

    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }
    public Map<String, List<Transaction>> getMonthlyStatement(Long accountId) {
        List<Transaction> transactions = transactionRepository
                .findByFromAccountIdOrToAccountId(accountId, accountId);

        return transactions.stream()
                .collect(Collectors.groupingBy(txn ->
                                txn.getTimestamp().getMonth() + " " + txn.getTimestamp().getYear(),
                        LinkedHashMap::new, // maintain order
                        Collectors.toList()
                ));
    }

}