package com.BankingSystem.BankingSystem.service;

import com.BankingSystem.BankingSystem.entity.Account;
import com.BankingSystem.BankingSystem.entity.Transaction;
import com.BankingSystem.BankingSystem.entity.User;
import com.BankingSystem.BankingSystem.repository.AccountRepository;
import com.BankingSystem.BankingSystem.repository.TransactionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class TransactionServiceTest {

    @Mock
    private AccountRepository accountRepository;
    @Mock private TransactionRepository transactionRepository;

    @InjectMocks
    private TransactionService transactionService;

    @Test
    public void testDeposit() {
        Account account = new Account(1L, "ACCT001", "SAVINGS", 1000.0, new User());
        when(accountRepository.findById(1L)).thenReturn(Optional.of(account));

        Transaction txn = transactionService.deposit(1L, 500.0);

        assertEquals("DEPOSIT", txn.getTransactionType());
        assertEquals(1500.0, account.getBalance());
    }
}