package com.cbc.bank.repository;

import com.cbc.bank.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    boolean existsByCustomerUsername(String username);
    void deleteByCustomerUsername(String username);

    Optional<Customer> findByCustomerUsername(String username);
}
