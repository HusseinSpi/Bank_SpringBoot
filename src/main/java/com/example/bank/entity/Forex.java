package com.example.bank.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "forex")
@Data
public class Forex {

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
