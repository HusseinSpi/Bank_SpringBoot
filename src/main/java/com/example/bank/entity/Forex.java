package com.example.bank.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "forex")
public class Forex {

    public Long getForexId() {
        return forexId;
    }

    public void setForexId(Long forexId) {
        this.forexId = forexId;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Double getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(Double exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "forex_id")
    private Long forexId;

    @Column(length = 10, nullable = false)
    private String currency;

    @Column(name = "exchange_rate", precision = 10, scale = 4, nullable = false)
    private Double exchangeRate;

    @Column
    private LocalDateTime timestamp;

    public Forex() {
    }

}
