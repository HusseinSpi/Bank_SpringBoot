package com.example.bank.controller;

import com.example.bank.entity.Loan;
import com.example.bank.service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * يتحكم في عمليات CRUD على كائنات Loan (القروض).
 */
@RestController
@RequestMapping("/api/loans")
public class LoanController {

    private final LoanService loanService;

    @Autowired
    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    /**
     * إنشاء Loan جديد.
     */
    @PostMapping
    public Loan createLoan(@RequestBody Loan loan) {
        return loanService.createLoan(loan);
    }

    /**
     * الحصول على Loan عبر معرّفه (ID).
     */
    @GetMapping("/{id}")
    public Loan getLoan(@PathVariable Long id) {
        return loanService.getLoanById(id);
    }

    /**
     * الحصول على جميع القروض.
     */
    @GetMapping
    public List<Loan> getAllLoans() {
        return loanService.getAllLoans();
    }

    /**
     * تعديل Loan موجود عبر ID.
     */
    @PutMapping("/{id}")
    public Loan updateLoan(@PathVariable Long id, @RequestBody Loan updatedLoan) {
        // لا نستطيع استخدام setLoanId(id). بدلاً من ذلك نمرر المعرف للـ Service.
        return loanService.updateLoan(id, updatedLoan);
    }

    /**
     * حذف Loan عبر ID.
     */
    @DeleteMapping("/{id}")
    public void deleteLoan(@PathVariable Long id) {
        loanService.deleteLoan(id);
    }
}
