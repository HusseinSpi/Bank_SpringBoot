package com.example.bank.dto;

import java.math.BigDecimal;
import java.util.Map;

public class ForexResponse {

    private boolean success;
    private String base;
    private String date;
    private Map<String, BigDecimal> rates;

    // -- Getters and Setters --

    public boolean isSuccess() {
        return success;
    }
    public void setSuccess(boolean success) {
        this.success = success;
    }
    public String getBase() {
        return base;
    }
    public void setBase(String base) {
        this.base = base;
    }
    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public Map<String, BigDecimal> getRates() {
        return rates;
    }
    public void setRates(Map<String, BigDecimal> rates) {
        this.rates = rates;
    }
}
