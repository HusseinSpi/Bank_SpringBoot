package com.example.bank.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "accounts")
public class Account {

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
    private BigDecimal balance;

    @Column(length = 10, nullable = false)
    private String currency;

    @Enumerated(EnumType.STRING)
    @Column(length = 20, nullable = false)
    private AccountStatus status;

    @Enumerated(EnumType.STRING)
    @Column(name = "account_type", length = 20, nullable = false)
    private AccountType accountType;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // Use the bridging entity CustomerAccount
    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    private List<CustomerAccount> customerAccounts = new ArrayList<>();

    // One-to-Many with Card
    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    private List<Card> cards = new ArrayList<>();

    public Account() {
    }

    // -- Getters --
    public Long getAccountId() { return accountId; }
    public String getAccountNumber() { return accountNumber; }
    public BigDecimal getBalance() { return balance; }
    public String getCurrency() { return currency; }
    public AccountStatus getStatus() { return status; }
    public AccountType getAccountType() { return accountType; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public List<CustomerAccount> getCustomerAccounts() { return customerAccounts; }
    public List<Card> getCards() { return cards; }

    // -- Setters --
    public void setAccountId(Long accountId) { this.accountId = accountId; }
    public void setAccountNumber(String accountNumber) { this.accountNumber = accountNumber; }
    public void setBalance(BigDecimal balance) { this.balance = balance; }
    public void setCurrency(String currency) { this.currency = currency; }
    public void setStatus(AccountStatus status) { this.status = status; }
    public void setAccountType(AccountType accountType) { this.accountType = accountType; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
    public void setCustomerAccounts(List<CustomerAccount> customerAccounts) { this.customerAccounts = customerAccounts; }
    public void setCards(List<Card> cards) { this.cards = cards; }
}
