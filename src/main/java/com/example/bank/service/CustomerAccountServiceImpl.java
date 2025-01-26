package com.example.bank.service;

import com.example.bank.entity.CustomerAccount;
import com.example.bank.repository.CustomerAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerAccountServiceImpl implements CustomerAccountService {

    private final CustomerAccountRepository customerAccountRepository;

    @Autowired
    public CustomerAccountServiceImpl(CustomerAccountRepository customerAccountRepository) {
        this.customerAccountRepository = customerAccountRepository;
    }

    @Override
    public CustomerAccount createCustomerAccount(CustomerAccount customerAccount) {
        return customerAccountRepository.save(customerAccount);
    }

    @Override
    public CustomerAccount getCustomerAccountById(Long id) {
        return customerAccountRepository.findById(id).orElse(null);
    }

    @Override
    public List<CustomerAccount> getAllCustomerAccounts() {
        return customerAccountRepository.findAll();
    }

    @Override
    public CustomerAccount updateCustomerAccount(Long id, CustomerAccount updatedCA) {
        CustomerAccount existingCA = customerAccountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("CustomerAccount not found with ID: " + id));

        // Update the fields you need
        existingCA.setCustomer(updatedCA.getCustomer());
        existingCA.setAccount(updatedCA.getAccount());

        return customerAccountRepository.save(existingCA);
    }

    @Override
    public void deleteCustomerAccount(Long id) {
        customerAccountRepository.deleteById(id);
    }
}
