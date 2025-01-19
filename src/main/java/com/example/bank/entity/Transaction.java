package com.example.bank.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
@Data
public class Transaction {
    public enum TransferMode {
        INTERNAL,
        EXTERNAL
    }
    public enum TransactionType {
        DEPOSIT,
        WITHDRAWAL,
        TRANSFER
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transaction_id")
    private Long transactionId;

    @Column(nullable = false, precision = 10, scale = 2)
    private Double amount;

    @Column(length = 10, nullable = false)
    private String currency;

    @Column
    private LocalDateTime timestamp;

    @Enumerated(EnumType.STRING)
    @Column(name = "transfer_mode", length = 10, nullable = false)
    private TransferMode transferMode; // e.g. INTERNAL, EXTERNAL

    @Enumerated(EnumType.STRING)
    @Column(name = "transaction_type", length = 10, nullable = false)
    private TransactionType transactionType; // e.g. DEPOSIT, WITHDRAWAL, TRANSFER

    @ManyToOne
    @JoinColumn(name = "destination_account")
    private Account destinationAccount;

    @ManyToOne
    @JoinColumn(name = "source_account")
    private Account sourceAccount;

    @ManyToOne
    @JoinColumn(name = "card_id")
    private Card card;

    public Transaction() {}

    public Transaction(Double amount, String currency, LocalDateTime timestamp,
                       TransferMode transferMode, TransactionType transactionType,
                       Account destinationAccount, Account sourceAccount, Card card) {
        this.amount = amount;
        this.currency = currency;
        this.timestamp = timestamp;
        this.transferMode = transferMode;
        this.transactionType = transactionType;
        this.destinationAccount = destinationAccount;
        this.sourceAccount = sourceAccount;
        this.card = card;
    }
}
