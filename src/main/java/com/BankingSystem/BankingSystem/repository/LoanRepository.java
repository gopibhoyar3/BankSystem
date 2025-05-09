package com.BankingSystem.BankingSystem.repository;

import com.BankingSystem.BankingSystem.entity.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {
    List<Loan> findByAccountIdAndIsPaidFalse(Long accountId);
}