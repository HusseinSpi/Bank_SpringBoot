package com.example.bank.service;

import com.example.bank.entity.Loan;
import com.example.bank.repository.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class LoanServiceImpl implements LoanService {

    private final LoanRepository loanRepository;

    @Autowired
    public LoanServiceImpl(LoanRepository loanRepository) {
        this.loanRepository = loanRepository;
    }

    @Override
    public Loan createLoan(Loan loan) {
        return loanRepository.save(loan);
    }

    @Override
    public Loan getLoanById(Long id) {
        return loanRepository.findById(id).orElse(null);
    }

    @Override
    public List<Loan> getAllLoans() {
        return loanRepository.findAll();
    }

    @Override
    public Loan updateLoan(Long id, Loan updatedLoan) {
        Loan existingLoan = loanRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Loan not found"));

        existingLoan.setCustomer(updatedLoan.getCustomer());
        existingLoan.setAmount(updatedLoan.getAmount());
        existingLoan.setCurrency(updatedLoan.getCurrency());
        existingLoan.setInterestRate(updatedLoan.getInterestRate());
        existingLoan.setLoanStatus(updatedLoan.getLoanStatus());
        existingLoan.setUpdatedAt(LocalDateTime.now());

        return loanRepository.save(existingLoan);
    }

    @Override
    public void deleteLoan(Long id) {
        loanRepository.deleteById(id);
    }
}
