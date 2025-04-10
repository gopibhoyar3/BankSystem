package com.BankingSystem.BankingSystem.repository;

import com.BankingSystem.BankingSystem.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByFromAccountIdOrToAccountId(Long fromId, Long toId);
    List<Transaction> findTop5ByFromAccountIdOrToAccountIdOrderByTimestampDesc(Long fromId, Long toId);
    boolean existsByFromAccountIdOrToAccountId(Long fromId, Long toId);
}