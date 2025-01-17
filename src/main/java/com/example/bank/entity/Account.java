package com.example.bank.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "accounts")
@Data
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

    @Column(nullable = false, precision = 10, scale = 2)
    private Double balance;

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
