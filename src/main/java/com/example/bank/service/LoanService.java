package com.example.bank.service;

import com.example.bank.entity.Loan;

import java.math.BigDecimal;
import java.util.List;

public interface LoanService {
    Loan createLoan(Loan loan);
    Loan getLoanById(Long id);
    List<Loan> getAllLoans();
    Loan updateLoan(Long id, Loan updatedLoan);

    void deleteLoan(Long id);
    Loan payLoan(Long loanId, BigDecimal paymentAmount);

}
