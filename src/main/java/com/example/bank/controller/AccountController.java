package com.example.bank.controller;

import com.example.bank.entity.Account;
import com.example.bank.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * يتحكم في عمليات CRUD على كائنات Account.
 */
@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    /**
     * إنشاء حساب جديد.
     * يتم إرسال بيانات الحساب في الـ Request Body.
     */
    @PostMapping
    public Account createAccount(@RequestBody Account account) {
        return accountService.createAccount(account);
    }

    /**
     * الحصول على حساب عبر معرّفه (ID).
     */
    @GetMapping("/{id}")
    public Account getAccount(@PathVariable Long id) {
        return accountService.getAccountById(id);
    }

    /**
     * الحصول على جميع الحسابات.
     */
    @GetMapping
    public List<Account> getAllAccounts() {
        return accountService.getAllAccounts();
    }

    /**
     * تعديل حساب موجود عبر ID.
     * لا نستطيع استخدام setAccountId(id) مباشرةً، بل نمرّر id للمنهجية في الخدمة (Service).
     */
    @PutMapping("/{id}")
    public Account updateAccount(@PathVariable Long id, @RequestBody Account updatedAccount) {
        // نقوم بإرسال المعرّف والبيانات الجديدة إلى الـ Service.
        return accountService.updateAccount(id, updatedAccount);
    }

    /**
     * حذف حساب عبر ID.
     */
    @DeleteMapping("/{id}")
    public void deleteAccount(@PathVariable Long id) {
        accountService.deleteAccount(id);
    }
}
