package com.cbc.bank.repository;

import com.cbc.bank.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository  extends JpaRepository<Transaction, Integer> {
    List<Transaction> findByCustomerCustomerUsername(String username);
}
