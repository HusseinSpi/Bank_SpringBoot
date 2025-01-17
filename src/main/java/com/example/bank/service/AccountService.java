package com.example.bank.service;

import com.example.bank.entity.Account;

import java.util.List;

public interface AccountService {
    Account createAccount(Account account);
    Account getAccountById(Long id);
    List<Account> getAllAccounts();
    Account updateAccount(Account account);
    void deleteAccount(Long id);
}
