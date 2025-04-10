package com.BankingSystem.BankingSystem.service;

import com.BankingSystem.BankingSystem.entity.Account;
import com.BankingSystem.BankingSystem.entity.Loan;
import com.BankingSystem.BankingSystem.entity.User;
import com.BankingSystem.BankingSystem.repository.AccountRepository;
import com.BankingSystem.BankingSystem.repository.LoanRepository;
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
public class LoanServiceTest {

    @Mock
    private AccountRepository accountRepository;
    @Mock private LoanRepository loanRepository;
    @Mock private TransactionRepository transactionRepository;

    @InjectMocks
    private LoanService loanService;

    @Test
    public void testRequestLoan() {
        Account account = new Account(1L, "ACCT001", "SAVINGS", 2000.0, new User());
        when(accountRepository.findById(1L)).thenReturn(Optional.of(account));

        Loan loan = loanService.requestLoan(1L, 1000.0, 5.0);

        assertEquals(1050.0, loan.getRemainingAmount());
        assertEquals(3000.0, account.getBalance());  // 2000 + 1000
    }
}