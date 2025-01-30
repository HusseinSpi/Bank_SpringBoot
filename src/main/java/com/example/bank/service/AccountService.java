package com.example.bank.service;

import com.example.bank.entity.Account;

import java.util.List;

public interface AccountService {
    Account createAccount(Account account);
    Account getAccountById(Long id);
//    Account getAccountWithCustomers(Long id);

    List<Account> getAllAccounts();
    Account updateAccount(Long id, Account updatedAccount);
    void deleteAccount(Long id);
}
