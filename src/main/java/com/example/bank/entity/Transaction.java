package com.example.bank.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
public class Transaction {

    @Override
    public String toString() {
        return "Transaction{" +
                "transactionId=" + transactionId +
                ", destinationAccount=" + destinationAccount +
                ", description='" + description + '\'' +
                ", amount=" + amount +
                ", currency='" + currency + '\'' +
                ", timestamp=" + timestamp +
                ", transferMode=" + transferMode +
                ", transactionType=" + transactionType +
                ", sourceAccount=" + sourceAccount +
                ", card=" + card +
                '}';
    }

    public enum TransferMode {
        INTERNAL,
        EXTERNAL
    }

    public enum TransactionType {
        DEPOSIT,
        WITHDRAWAL,
        TransactionType, TRANSFER
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transaction_id")
    private Long transactionId;

    @Column(length = 255)
    private String description;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal amount;
    @Column(length = 10, nullable = false)
    private String currency;

    @Column
    private LocalDateTime timestamp;

    @Enumerated(EnumType.STRING)
    @Column(name = "transfer_mode", length = 10, nullable = false)
    private TransferMode transferMode;

    @Enumerated(EnumType.STRING)
    @Column(name = "transaction_type", length = 10, nullable = false)
    private TransactionType transactionType;

    @ManyToOne
    @JoinColumn(name = "destination_account", nullable = false)
    private Account destinationAccount;


    @ManyToOne
    @JoinColumn(name = "source_account")
    private Account sourceAccount;

    @ManyToOne
    @JoinColumn(name = "card_id")
    private Card card;

    public Transaction() {
    }

    public Transaction(BigDecimal amount,
                       String currency,
                       LocalDateTime timestamp,
                       TransferMode transferMode,
                       TransactionType transactionType,
                       Account destinationAccount,
                       Account sourceAccount,
                       Card card) {

        this.amount = amount;
        this.currency = currency;
        this.timestamp = timestamp;
        this.transferMode = transferMode;
        this.transactionType = transactionType;
        this.destinationAccount = destinationAccount;
        this.sourceAccount = sourceAccount;
        this.card = card;
    }
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public Transaction(BigDecimal amount,
                       String currency,
                       TransferMode transferMode,
                       TransactionType transactionType,
                       Account destinationAccount) {
        this.amount = amount;
        this.currency = currency;
        this.transferMode = transferMode;
        this.transactionType = transactionType;
        this.destinationAccount = destinationAccount;
    }
    // -- Getters --

    public Long getTransactionId() {
        return transactionId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getCurrency() {
        return currency;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public TransferMode getTransferMode() {
        return transferMode;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public Account getDestinationAccount() {
        return destinationAccount;
    }

    public Account getSourceAccount() {
        return sourceAccount;
    }

    public Card getCard() {
        return card;
    }

    // -- Setters --

    @SuppressWarnings("unused")
    private void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public void setTransferMode(TransferMode transferMode) {
        this.transferMode = transferMode;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public void setDestinationAccount(Account destinationAccount) {
        this.destinationAccount = destinationAccount;
    }

    public void setSourceAccount(Account sourceAccount) {
        this.sourceAccount = sourceAccount;
    }

    public void setCard(Card card) {
        this.card = card;
    }
}
