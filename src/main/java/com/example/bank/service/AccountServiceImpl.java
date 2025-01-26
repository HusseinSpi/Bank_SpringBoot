package com.example.bank.service;

import com.example.bank.entity.Account;
import com.example.bank.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public Account createAccount(Account account) {
        return accountRepository.save(account);
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
