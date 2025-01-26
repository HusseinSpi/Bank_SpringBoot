package com.example.bank.service;

import com.example.bank.entity.CustomerAccount;

import java.util.List;

public interface CustomerAccountService {
    CustomerAccount createCustomerAccount(CustomerAccount customerAccount);
    CustomerAccount getCustomerAccountById(Long id);
    List<CustomerAccount> getAllCustomerAccounts();
    CustomerAccount updateCustomerAccount(Long id, CustomerAccount updatedCA);
    void deleteCustomerAccount(Long id);
}
