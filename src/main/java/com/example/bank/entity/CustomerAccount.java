package com.example.bank.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "customers_accounts")
@Data
public class CustomerAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_account_id")
    private Long customerAccountId;

    @Column(name = "account_id", nullable = false)
    private Long accountId;

    @Column(name = "customer_id", nullable = false)
    private Long customerId;

    // If you want direct entity references:
    // @ManyToOne
    // @JoinColumn(name = "account_id", insertable=false, updatable=false)
    // private Account account;

    // @ManyToOne
    // @JoinColumn(name = "customer_id", insertable=false, updatable=false)
    // private Customer customer;

    public CustomerAccount() {
    }

}
