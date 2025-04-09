package com.BankingSystem.BankingSystem.repository;

import com.BankingSystem.BankingSystem.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // You can add custom queries here if needed later
}