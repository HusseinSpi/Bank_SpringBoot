package com.example.bank.service;

import com.example.bank.entity.Account;
import com.example.bank.entity.Customer;
import com.example.bank.entity.CustomerAccount;
import com.example.bank.repository.AccountRepository;
import com.example.bank.repository.CustomerAccountRepository;
import com.example.bank.repository.CustomerRepository;
import jakarta.transaction.Transactional;
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
    @Transactional
    public Account createAccount(Account request) {
        // Parse and validate enums
        Account.AccountType accountType;
        Account.AccountStatus accountStatus;
        Account.Currency currency;

        try {
            accountType = request.getAccountType();
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid account type: " + request.getAccountType());
        }

        try {
            accountStatus = request.getStatus();
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid account status: " + request.getStatus());
        }

        try {
            currency = request.getCurrency();
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid currency: " + request.getCurrency());
        }

        // Validate customer IDs based on account type
        List<Long> customerIds = request.getCustomerIds();

        if (accountType == Account.AccountType.JOINT) {
            if (customerIds.size() != 2) {
                throw new IllegalArgumentException("Joint accounts must have exactly two customer IDs.");
            }
        } else {
            if (customerIds.size() != 1) {
                throw new IllegalArgumentException("Non-joint accounts must have exactly one customer ID.");
            }
        }

        // Retrieve customer entities
        List<Customer> customers = customerRepository.findAllById(customerIds);
        if (customers.size() != customerIds.size()) {
                throw new IllegalArgumentException("Non-joint accounts must have exactly one customer ID.");

        }

        // Create and save the Account entity
        Account account = new Account();
        account.setCurrency(currency);
        account.setStatus(accountStatus);
        account.setAccountType(accountType);
        // Other fields like balance and accountNumber are handled via @PrePersist

        Account savedAccount = accountRepository.save(account);

        // Create and save CustomerAccount associations
        for (Customer customer : customers) {
            CustomerAccount customerAccount = new CustomerAccount();
            customerAccount.setCustomer(customer);
            customerAccount.setAccount(savedAccount);
            customerAccountRepository.save(customerAccount);
        }

        return savedAccount;
    }

    @Override
    public Account getAccountById(Long id) {
        return accountRepository.findById(id).orElse(null);
    }
//    @Override
//    @Transactional
//    public Account getAccountWithCustomers(Long id) {
//        Account account = accountRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("Account not found with ID: " + id));
//
//        List<CustomerAccount> customerAccounts = customerAccountRepository.findByAccountId(id);
//
//        // Transform CustomerAccount to CustomerDTO
//        List<Customer> customers = customerAccounts.stream()
//                .map(ca -> {
//                    Customer customer = ca.getCustomer();
//                    return new Customer(
//                            customer.getCustomerId(),
//                            customer.getUsername(),
//                            customer.getEmail()
//
//                    );
//                })
//                .collect(Collectors.toList());
//
//
//        Account account1 = new Account();
//        account1.setAccountId(account.getAccountId());
//        account1.setAccountNumber(account.getAccountNumber());
//        account1.setBalance(account.getBalance());
//        account1.setCurrency(account.getCurrency());
//        account1.setStatus(account.getStatus());
//        account1.setAccountType(account.getAccountType());
//        account1.setCreatedAt(account.getCreatedAt());
//        account1.setUpdatedAt(account.getUpdatedAt());
//        account1.setCustomers(customers);
//
//        return account1;
//    }
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
