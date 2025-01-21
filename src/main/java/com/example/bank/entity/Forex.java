package com.example.bank.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "forex")
public class Forex {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "forex_id")
    private Long forexId;

    @Column(length = 10, nullable = false)
    private String currency;

    @Column(name = "exchange_rate", precision = 10, scale = 4, nullable = false)
    private BigDecimal exchangeRate;

    @Column(name = "timestamp", columnDefinition = "DATETIME")
    private LocalDateTime timestamp;

    public Forex() {
    }

    public Long getForexId() {
        return forexId;
    }

    public String getCurrency() {
        return currency;
    }

    public BigDecimal getExchangeRate() {
        return exchangeRate;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setForexId(Long forexId) {
        this.forexId = forexId;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public void setExchangeRate(BigDecimal exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

}
