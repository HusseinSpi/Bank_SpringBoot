package com.example.bank.controller;

import com.example.bank.entity.Loan;
import com.example.bank.service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/loans")
public class LoanController {

    private final LoanService loanService;

    @Autowired
    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    @PostMapping
    public Loan createLoan(@RequestBody Loan loan) {
        return loanService.createLoan(loan);
    }

    @GetMapping("/{id}")
    public Loan getLoan(@PathVariable Long id) {
        return loanService.getLoanById(id);
    }

    @GetMapping
    public List<Loan> getAllLoans() {
        return loanService.getAllLoans();
    }

    @PutMapping("/{id}")
    public Loan updateLoan(@PathVariable Long id, @RequestBody Loan loan) {
        loan.setLoanId(id);
        return loanService.updateLoan(loan);
    }

    @DeleteMapping("/{id}")
    public void deleteLoan(@PathVariable Long id) {
        loanService.deleteLoan(id);
    }
}
