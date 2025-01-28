package com.example.bank.service;

import com.example.bank.entity.Account;
import com.example.bank.entity.Customer;
import com.example.bank.entity.CustomerAccount;
import com.example.bank.repository.AccountRepository;
import com.example.bank.repository.CustomerAccountRepository;
import com.example.bank.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final CustomerRepository customerRepository;
    private final CustomerAccountRepository customerAccountRepository;
    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository,
                              CustomerRepository customerRepository,
                              CustomerAccountRepository customerAccountRepository) {
        this.accountRepository = accountRepository;
        this.customerRepository = customerRepository;
        this.customerAccountRepository = customerAccountRepository;
    }
    @Override
    public Account createAccount(Long customerId, Account account) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found with ID: " + customerId));

        Account savedAccount = accountRepository.save(account);

        CustomerAccount customerAccount = new CustomerAccount();
        customerAccount.setCustomer(customer);
        customerAccount.setAccount(savedAccount);
        customerAccountRepository.save(customerAccount);

        List<CustomerAccount> allCustomerAccounts = customerAccountRepository.findAll();
        System.out.println("Customer_Account Table Contents:");
        for (CustomerAccount ca : allCustomerAccounts) {
            System.out.println("CustomerAccount ID: " + ca.getCustomerAccountId() +
                    ", Customer ID: " + ca.getCustomer().getCustomerId() +
                    ", Account ID: " + ca.getAccount().getAccountId());
        }

        return savedAccount;
    }
    @Override
    public Account getAccountById(Long id) {
        return accountRepository.findById(id).orElse(null);
    }

    @Override
    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    @Override
    public Account updateAccount(Long id, Account updatedAccount) {
        Account existingAccount = accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Account not found"));
        existingAccount.setCurrency(updatedAccount.getCurrency());
        existingAccount.setStatus(updatedAccount.getStatus());
        existingAccount.setAccountType(updatedAccount.getAccountType());
        existingAccount.setUpdatedAt(LocalDateTime.now());
        return accountRepository.save(existingAccount);
    }

    @Override
    public void deleteAccount(Long id) {
        accountRepository.deleteById(id);
    }
}
