package com.cbc.bank.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Data
@Setter @Getter @NoArgsConstructor
@Table(name="cbc_customer_table")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "customer_seq_generator")
    @SequenceGenerator(name = "customer_seq_generator", sequenceName = "customer_seq", allocationSize = 1)
    @Column(name="account_number")
    private int customerId;

    @Column(name="customer_name")
    private String customerName;

    @Column(name="contact_number")
    private long customerNumber;

    @Column(name="username" ,unique = true)
    private String customerUsername;

    @Column(name="password")
    private String customerPassword;

    @Column(name="balance")
    private float customerBalance;
	
	@OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Transaction> transactions;
    public Customer(int customerId, String customerName, long customerNumber, String customerUsername, String customerPassword, float customerBalance) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.customerNumber = customerNumber;
        this.customerUsername = customerUsername;
        this.customerPassword = customerPassword;
        this.customerBalance = customerBalance;
    }
}
