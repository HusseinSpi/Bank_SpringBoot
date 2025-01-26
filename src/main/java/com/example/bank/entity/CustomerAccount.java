package com.example.bank.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "customer_account")
public class CustomerAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_account_id")
    private Long customerAccountId;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    // Constructors
    public CustomerAccount() {
    }

    // Getters
    public Long getCustomerAccountId() {
        return customerAccountId;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Account getAccount() {
        return account;
    }

    // Setters
    // Usually you do not publicly expose the ID setter,
    // but it is shown here if needed by your use-case.
    public void setCustomerAccountId(Long customerAccountId) {
        this.customerAccountId = customerAccountId;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
