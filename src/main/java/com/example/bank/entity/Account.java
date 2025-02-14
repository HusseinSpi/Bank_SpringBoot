package com.example.bank.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Entity
@Table(name = "accounts")
public class Account {

    public Account(String accountNumber, Currency currency, AccountStatus status, AccountType accountType, List<Card> cards) {
        this.accountNumber = accountNumber;
        this.currency = currency;
        this.status = status;
        this.accountType = accountType;
        this.cards = cards;
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

    public enum Currency {
        USD,
        EUR,
        GBP,
        NIS
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id")
    private Long accountId;

    @Override
    public String toString() {
        return "Account{" +
                "accountId=" + accountId +
                ", accountNumber='" + accountNumber + '\'' +
                ", balance=" + balance +
                ", currency=" + currency +
                ", status=" + status +
                ", accountType=" + accountType +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", cards=" + cards +
                '}';
    }

    @Column(name = "account_number", nullable = true, length = 6, unique = true)
    private String accountNumber;

    @Column(nullable = false)
    private BigDecimal balance;

    @Enumerated(EnumType.STRING)
    @Column(name = "currency", length = 10, nullable = false)
    private Currency currency;

    // حالة الحساب
    @Enumerated(EnumType.STRING)
    @Column(length = 20, nullable = false)
    private AccountStatus status;

    // نوع الحساب
    @Enumerated(EnumType.STRING)
    @Column(name = "account_type", length = 20, nullable = false)
    private AccountType accountType;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;


    // العلاقة مع الكروت
    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    private List<Card> cards = new ArrayList<>();

    public Account() {
        this.balance = BigDecimal.ZERO;
    }

    @PrePersist
    public void prePersist() {
        if (this.balance == null) {
            this.balance = BigDecimal.ZERO;
        }
        if (this.accountNumber == null) {
            this.accountNumber = generateRandom6Digits();
        }
        if (this.createdAt == null) {
            this.createdAt = LocalDateTime.now();
        }
    }

    private String generateRandom6Digits() {
        Random random = new Random();
        return String.format("%06d", random.nextInt(1_000_000));
    }

    // -- Getters --

    public Long getAccountId() {
        return accountId;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public Currency getCurrency() {
        return currency;
    }

    public AccountStatus getStatus() {
        return status;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

//    public List<CustomerAccount> getCustomerAccounts() {
//        return customerAccounts;
//    }

    public List<Card> getCards() {
        return cards;
    }

    // -- Setters --


    @SuppressWarnings("unused")
    private void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    @SuppressWarnings("unused")
    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }


    @SuppressWarnings("unused")
    private void setBalance(BigDecimal balance) {
        this.balance=balance;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public void setStatus(AccountStatus status) {
        this.status = status;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }


    public void setCards(List<Card> cards) {
        this.cards = cards;
    }

    public void deposit(BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("The deposited amount must be a positive number.");
        }
        this.balance = this.balance.add(amount);
    }

    public void withdraw(BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("The withdrawn amount must be a positive number.");
        }
        if (this.balance.compareTo(amount) < 0) {
            throw new IllegalArgumentException("Current balance does not allow withdrawal.");
        }
        if (this.status != AccountStatus.ACTIVE) {
            throw new IllegalArgumentException("Account is currently inactive please contact your bank.");
        }
        this.balance = this.balance.subtract(amount);
    }
}
