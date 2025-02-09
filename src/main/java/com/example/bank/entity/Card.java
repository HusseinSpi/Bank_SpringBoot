package com.example.bank.entity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonFormat;

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
    @JsonBackReference
    private Account account;

    @Column(length = 100)
    private String name;
    @Column(length = 5)
    private String password;

    @Column(name = "card_number", nullable = true, length = 16, unique = true)
    private String cardNumber;

    @Column(name = "expiration_date", nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate expirationDate;

    @Column(length = 4, nullable = false)
    private String ccv;

    @Enumerated(EnumType.STRING)
    @Column(name = "card_status", length = 10, nullable = false)
    private CardStatus cardStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "card_type", length = 10, nullable = false)
    private CardType cardType;

    @Column(name = "created_at", columnDefinition = "DATETIME")
    private LocalDateTime createdAt;

    @Column(name = "updated_at", columnDefinition = "DATETIME")
    private LocalDateTime updatedAt;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Card() {
    }

    // -- Getters --

    public Long getCardId() {
        return cardId;
    }

    public Account getAccount() {
        return account;
    }

    public String getName() {
        return name;
    }

    public String getCardNumber() {
        return cardNumber;
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

    // -- Setters --

    @SuppressWarnings("unused")
    private void setCardId(Long cardId) {
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

    public void setCardStatus(CardStatus cardStatus) {
        this.cardStatus = cardStatus;
    }

    public void setCardType(CardType cardType) {
        this.cardType = cardType;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
