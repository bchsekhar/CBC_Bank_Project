package com.cbc.bank.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.util.Date;

@Entity
@Data
@Setter @Getter @NoArgsConstructor
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "transaction_seq_generator")
    @SequenceGenerator(name = "transaction_seq_generator", sequenceName = "transaction_seq", allocationSize = 1)
    @Column(name = "transaction_id")
    private int transactionId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "account_number", referencedColumnName = "account_number", nullable = false)
    private Customer customer;

    @Column(name = "account_number", insertable = false, updatable = false, nullable = false)
    private int accountNumber;

    @Column(name = "transaction_type", nullable = false)
    private String transactionType;

    @Column(name = "amount", nullable = false)
    private Double amount;

    @Temporal(TemporalType.DATE)
    @Column(name = "transaction_date", nullable = false)
    private Date transactionDate;


	public Transaction(int transactionId, int accountNumber, String transactionType, Double amount,
                       Date transactionDate) {
        this.transactionId = transactionId;
        this.accountNumber = accountNumber;
        this.transactionType = transactionType;
        this.amount = amount;
        this.transactionDate = transactionDate;
    }

}
