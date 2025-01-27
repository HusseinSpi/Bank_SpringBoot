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
        // Fetch the customer by ID
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found with ID: " + customerId));

        // Save the account
        Account savedAccount = accountRepository.save(account);

        // Create a CustomerAccount linking the customer and account
        CustomerAccount customerAccount = new CustomerAccount();
        customerAccount.setCustomer(customer);
        customerAccount.setAccount(savedAccount);

        // Save the CustomerAccount
        customerAccountRepository.save(customerAccount);

        // Fetch all CustomerAccount entries and print to console
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
        // نجلب الحساب الأصلي من قاعدة البيانات
        Account existingAccount = accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        // نحدّث الحقول المسموح تعديلها فقط:
        existingAccount.setCurrency(updatedAccount.getCurrency());
        existingAccount.setStatus(updatedAccount.getStatus());
        existingAccount.setAccountType(updatedAccount.getAccountType());
        existingAccount.setUpdatedAt(LocalDateTime.now());

        // (لا نعدل على الرصيد إلا بطرق خاصة، أو حسب الحاجة)
        // (لا نعدل accountNumber ولا accountId لأنها ممنوعة من التغيير)

        return accountRepository.save(existingAccount);
    }

    @Override
    public void deleteAccount(Long id) {
        accountRepository.deleteById(id);
    }
}
