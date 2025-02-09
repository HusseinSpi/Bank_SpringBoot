package com.example.bank.controller;

import com.example.bank.entity.Loan;
import com.example.bank.service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/loans")
public class LoanController {

    private final LoanService loanService;

    @Autowired
    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }
    public class PaymentRequest {
        private BigDecimal paymentAmount;
        // getters and setters
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
    public Loan updateLoan(@PathVariable Long id, @RequestBody Loan updatedLoan) {
        return loanService.updateLoan(id, updatedLoan);
    }

    @DeleteMapping("/{id}")
    public void deleteLoan(@PathVariable Long id) {
        loanService.deleteLoan(id);
    }

    @PostMapping("/{loanId}/pay")
    public Loan payLoan(@PathVariable Long loanId, @RequestBody Map<String, BigDecimal> payload) {
        BigDecimal paymentAmount = payload.get("paymentAmount");
        return loanService.payLoan(loanId, paymentAmount);
    }

}
