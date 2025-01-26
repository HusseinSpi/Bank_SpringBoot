package com.example.bank.dto;

public class CustomerAccountRequestDto {
    private Long customerId;
    private Long accountId;

    public Long getCustomerId() {
        return customerId;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }
}
