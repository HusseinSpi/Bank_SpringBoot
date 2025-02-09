package com.example.bank.service;

import com.example.bank.entity.Account;
import com.example.bank.entity.Loan;
import com.example.bank.repository.AccountRepository;
import com.example.bank.repository.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;


@Service
public class LoanServiceImpl implements LoanService {

    private final LoanRepository loanRepository;
    private final AccountRepository accountRepository;

    @Autowired
    public LoanServiceImpl(LoanRepository loanRepository, AccountRepository accountRepository) {
        this.loanRepository = loanRepository;
        this.accountRepository = accountRepository;
    }

    @Override
    public Loan createLoan(Loan loan) {
    Account destinationAccount =null;
        destinationAccount = accountRepository.findByAccountNumber(loan.getAccount().getAccountNumber())
                .orElseThrow(() -> new RuntimeException("Destination account not found with account number: " + loan.getAccount().getAccountNumber()));
        System.out.println(destinationAccount.toString());
        loan.setAccount(destinationAccount);
        BigDecimal interest = loan.getAmount()
                .multiply(loan.getInterestRate())
                .divide(BigDecimal.valueOf(100));
        BigDecimal totalDue = loan.getAmount().add(interest);
        loan.setRemainingAmount(totalDue);

        loan.setCreatedAt(LocalDateTime.now());
        loan.setLoanStatus(Loan.LoanStatus.ACTIVE);
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

    @Override
    public Loan payLoan(Long loanId, BigDecimal paymentAmount) {
        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new RuntimeException("Loan not found with id: " + loanId));

        if (loan.getLoanStatus() == Loan.LoanStatus.CLOSED) {
            throw new RuntimeException("Loan is already closed.");
        }
        if (paymentAmount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("Payment amount must be positive.");
        }

        // Get current remaining amount; calculate it if not already set.
        BigDecimal remaining = loan.getRemainingAmount();
        if (remaining == null) {
            BigDecimal interest = loan.getAmount()
                    .multiply(loan.getInterestRate())
                    .divide(BigDecimal.valueOf(100));
            remaining = loan.getAmount().add(interest);
        }

        // Get the customer's account and withdraw the payment amount.
        Account account = loan.getAccount();
        account.withdraw(paymentAmount, null);

        // Calculate new remaining amount after applying the payment.
        BigDecimal newRemaining = remaining.subtract(paymentAmount);

        // Check for overpayment.
        if (newRemaining.compareTo(BigDecimal.ZERO) <= 0) {
            // Calculate overpayment amount (if any)
            BigDecimal overPayment = newRemaining.compareTo(BigDecimal.ZERO) < 0
                    ? newRemaining.abs()
                    : BigDecimal.ZERO;
            // Refund the overpayment to the customer's account.
            if (overPayment.compareTo(BigDecimal.ZERO) > 0) {
                account.deposit(overPayment);
            }
            // Mark the loan as fully paid.
            loan.setRemainingAmount(BigDecimal.ZERO);
            loan.setLoanStatus(Loan.LoanStatus.CLOSED);
        } else {
            // Loan is partially paid; update the remaining amount.
            loan.setRemainingAmount(newRemaining);
        }

        loan.setUpdatedAt(LocalDateTime.now());
        return loanRepository.save(loan);
    }
}
