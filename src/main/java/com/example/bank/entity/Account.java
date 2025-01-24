package com.example.bank.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;



@Entity
@Table(name = "accounts")
public class Account {

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }

    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public void setStatus(AccountStatus status) {
        this.status = status;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public enum AccountStatus {
        ACTIVE,
        RESTRICTED,
        SUSPENDED
    }
    public enum AccountType {
        CHECKING,
        CD,
        JOINT,
        MONEY_MARKET
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id")
    private Long accountId;

    @Column(name = "account_number", nullable = false, length = 50)
    private String accountNumber;

    @Column(nullable = false)
    private BigDecimal balance; // remove precision and scale




    @Column(length = 10, nullable = false)
    private String currency;

    @Enumerated(EnumType.STRING)
    @Column(length = 20, nullable = false)
    private AccountStatus status;  // e.g. ACTIVE, RESTRICTED, SUSPENDED

    @Enumerated(EnumType.STRING)
    @Column(name = "account_type", length = 20, nullable = false)
    private AccountType accountType;  // e.g. CHECKING, CD, JOINT, MONEY_MARKET

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // Many-to-Many with Customer via CustomerAccount
    @ManyToMany(mappedBy = "accounts")
    private List<Customer> customers = new ArrayList<>();

    // One-to-Many with Card
    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    private List<Card> cards = new ArrayList<>();

    public Account() {
    }
}
