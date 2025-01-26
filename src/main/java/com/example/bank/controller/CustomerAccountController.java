package com.example.bank.controller;

import com.example.bank.dto.CustomerAccountRequestDto;
import com.example.bank.entity.Customer;
import com.example.bank.entity.Account;
import com.example.bank.entity.CustomerAccount;
import com.example.bank.repository.CustomerRepository;
import com.example.bank.repository.AccountRepository;
import com.example.bank.service.CustomerAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customer-accounts")
public class CustomerAccountController {

    private final CustomerAccountService customerAccountService;
    private final CustomerRepository customerRepository;
    private final AccountRepository accountRepository;

    @Autowired
    public CustomerAccountController(CustomerAccountService customerAccountService,
                                     CustomerRepository customerRepository,
                                     AccountRepository accountRepository) {
        this.customerAccountService = customerAccountService;
        this.customerRepository = customerRepository;
        this.accountRepository = accountRepository;
    }

    // CREATE
    @PostMapping
    public CustomerAccount createCustomerAccount(@RequestBody CustomerAccountRequestDto dto) {
        // 1) Fetch the existing Customer and Account by ID
        Customer customer = customerRepository.findById(dto.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Customer not found with ID " + dto.getCustomerId()));
        Account account = accountRepository.findById(dto.getAccountId())
                .orElseThrow(() -> new RuntimeException("Account not found with ID " + dto.getAccountId()));

        // 2) Create and populate the CustomerAccount entity
        CustomerAccount newCustomerAccount = new CustomerAccount();
        newCustomerAccount.setCustomer(customer);
        newCustomerAccount.setAccount(account);

        // 3) Save via the service
        return customerAccountService.createCustomerAccount(newCustomerAccount);
    }

    // READ (single)
    @GetMapping("/{id}")
    public CustomerAccount getCustomerAccount(@PathVariable Long id) {
        return customerAccountService.getCustomerAccountById(id);
    }

    // READ (all)
    @GetMapping
    public List<CustomerAccount> getAllCustomerAccounts() {
        return customerAccountService.getAllCustomerAccounts();
    }

    // UPDATE
    @PutMapping("/{id}")
    public CustomerAccount updateCustomerAccount(@PathVariable Long id,
                                                 @RequestBody CustomerAccountRequestDto dto) {
        // 1) Fetch the *existing* CustomerAccount from DB
        CustomerAccount existingCA = customerAccountService.getCustomerAccountById(id);
        if (existingCA == null) {
            throw new RuntimeException("CustomerAccount not found with ID: " + id);
        }

        // 2) Fetch new Customer/Account from DB
        Customer customer = customerRepository.findById(dto.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Customer not found with ID " + dto.getCustomerId()));
        Account account = accountRepository.findById(dto.getAccountId())
                .orElseThrow(() -> new RuntimeException("Account not found with ID " + dto.getAccountId()));

        // 3) Update fields
        existingCA.setCustomer(customer);
        existingCA.setAccount(account);

        // 4) Save and return
        return customerAccountService.updateCustomerAccount(id, existingCA);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public void deleteCustomerAccount(@PathVariable Long id) {
        customerAccountService.deleteCustomerAccount(id);
    }
}
