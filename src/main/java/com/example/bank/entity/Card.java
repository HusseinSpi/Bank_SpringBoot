package com.example.bank.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "cards")
public class Card {

    public enum CardStatus {
        ACTIVE,
        BLOCKED
    }

    public enum CardType {
        DEBIT,
        CREDIT
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "card_id")
    private Long cardId;

    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    @Column(length = 100)
    private String name;

    @Column(name = "card_number", length = 16, nullable = false)
    private String cardNumber;

    @Column(name = "expiration_date", nullable = false)
    private LocalDate expirationDate;

    @Column(length = 4, nullable = false)
    private String ccv;

    @Enumerated(EnumType.STRING)
    @Column(name = "card_status", length = 10, nullable = false)
    private CardStatus cardStatus; // e.g. ACTIVE, BLOCKED

    @Enumerated(EnumType.STRING)
    @Column(name = "card_type", length = 10, nullable = false)
    private CardType cardType; // DEBIT, CREDIT

    @Column(name = "created_at", columnDefinition = "DATETIME")
    private LocalDateTime createdAt;

    @Column(name = "updated_at", columnDefinition = "DATETIME")
    private LocalDateTime updatedAt;

    public Card() {
    }

    public Long getCardId() {
        return cardId;
    }

    public Account getAccount() {
        return account;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public String getName() {
        return name;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public String getCcv() {
        return ccv;
    }

    public CardStatus getCardStatus() {
        return cardStatus;
    }

    public CardType getCardType() {
        return cardType;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setCardId(Long cardId) {
        this.cardId = cardId;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }

    public void setCcv(String ccv) {
        this.ccv = ccv;
    }

    public void setCardType(CardType cardType) {
        this.cardType = cardType;
    }

    public void setCardStatus(CardStatus cardStatus) {
        this.cardStatus = cardStatus;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

}
