package com.example.bank.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "customers_accounts")
public class CustomerAccount {
    public Long getCustomerAccountId() {
        return customerAccountId;
    }

    public void setCustomerAccountId(Long customerAccountId) {
        this.customerAccountId = customerAccountId;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

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
