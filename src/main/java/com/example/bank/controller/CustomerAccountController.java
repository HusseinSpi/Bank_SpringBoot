package com.example.bank.controller;

import com.example.bank.entity.CustomerAccount;
import com.example.bank.service.CustomerAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customer-accounts")
public class CustomerAccountController {

    private final CustomerAccountService customerAccountService;

    @Autowired
    public CustomerAccountController(CustomerAccountService customerAccountService) {
        this.customerAccountService = customerAccountService;
    }

    @PostMapping
    public CustomerAccount createCustomerAccount(@RequestBody CustomerAccount ca) {
        return customerAccountService.createCustomerAccount(ca);
    }

    @GetMapping("/{id}")
    public CustomerAccount getCustomerAccount(@PathVariable Long id) {
        return customerAccountService.getCustomerAccountById(id);
    }

    @GetMapping
    public List<CustomerAccount> getAllCustomerAccounts() {
        return customerAccountService.getAllCustomerAccounts();
    }

    @PutMapping("/{id}")
    public CustomerAccount updateCustomerAccount(@PathVariable Long id, @RequestBody CustomerAccount ca) {
        ca.setCustomerAccountId(id);
        return customerAccountService.updateCustomerAccount(ca);
    }

    @DeleteMapping("/{id}")
    public void deleteCustomerAccount(@PathVariable Long id) {
        customerAccountService.deleteCustomerAccount(id);
    }
}
