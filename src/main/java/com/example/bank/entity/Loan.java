package com.example.bank.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "loan")
@Data
public class Loan {
    public enum LoanStatus {
        ACTIVE,
        CLOSED
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "loan_id")
    private Long loanId;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @Column(nullable = false, precision = 10, scale = 2)
    private Double amount;

    @Column(length = 10, nullable = false)
    private String currency;

    @Column(name = "interest_rate", precision = 5, scale = 2, nullable = false)
    private Double interestRate;

    @Enumerated(EnumType.STRING)
    @Column(name = "loan_status", length = 10, nullable = false)
    private LoanStatus loanStatus; // e.g. ACTIVE, CLOSED

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public Loan() {
    }

}
