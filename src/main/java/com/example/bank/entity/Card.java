package com.example.bank.entity;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.LocalDate;

@Entity
@Table(name = "cards")
@Data
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
    private CardType cardType; // e.g. DEBIT, CREDIT

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public Card() {
    }

}
