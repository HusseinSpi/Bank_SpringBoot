package com.example.bank.service;

import com.example.bank.entity.Loan;

import java.util.List;

public interface LoanService {
    Loan createLoan(Loan loan);
    Loan getLoanById(Long id);
    List<Loan> getAllLoans();
    Loan updateLoan(Loan loan);
    void deleteLoan(Long id);
}
