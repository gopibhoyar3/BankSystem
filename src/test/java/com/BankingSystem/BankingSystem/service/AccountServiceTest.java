package com.BankingSystem.BankingSystem.service;

import com.BankingSystem.BankingSystem.entity.Account;
import com.BankingSystem.BankingSystem.entity.User;
import com.BankingSystem.BankingSystem.repository.AccountRepository;
import com.BankingSystem.BankingSystem.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class AccountServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AccountService accountService;

    @Test
    public void testCreateAccount() {
        User mockUser = new User();

        when(userRepository.findById(1L)).thenReturn(Optional.of(mockUser));

        Account result = accountService.createAccount(1L, "SAVINGS", 5000.0);

        assertEquals("SAVINGS", result.getAccountType());
        assertEquals(5000.0, result.getBalance());
        assertNotNull(result.getAccountNumber());
    }

    @Test
    public void testDeleteAccountWithNonZeroBalance_ThrowsException() {
        Account account = new Account(1L, "ACCT001", "SAVINGS", 100.0, new User());
        when(accountRepository.findById(1L)).thenReturn(Optional.of(account));

        assertThrows(ResponseStatusException.class, () -> {
            accountService.deleteAccount(1L);
        });
    }
}