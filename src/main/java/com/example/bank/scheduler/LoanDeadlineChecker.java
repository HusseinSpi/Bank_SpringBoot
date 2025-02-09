package com.example.bank.scheduler;

import com.example.bank.entity.Account;
import com.example.bank.entity.Loan;
import com.example.bank.repository.AccountRepository;
import com.example.bank.repository.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class LoanDeadlineChecker {

    private final LoanRepository loanRepository;
    private final AccountRepository accountRepository;

    @Autowired
    public LoanDeadlineChecker(LoanRepository loanRepository, AccountRepository accountRepository) {
        this.loanRepository = loanRepository;
        this.accountRepository = accountRepository;
    }

    // This cron expression means: At 11:00 AM every day.
//    @Scheduled(cron = "0 20 15 * * *")
//            15:20:00 every day
    @Scheduled(cron = "0 56 15 * * *")
    public void checkLoanDeadlines() {
        LocalDateTime now = LocalDateTime.now();

        List<Loan> loans = loanRepository.findAll();

        for (Loan loan : loans) {
            System.out.println(loan.toString());
            if (loan.getDueDate() != null && loan.getLoanStatus()!= Loan.LoanStatus.CLOSED && loan.getDueDate().isBefore(now)) {
                Account account = loan.getAccount();
                if (account != null && account.getStatus() != Account.AccountStatus.RESTRICTED) {
                    account.setStatus(Account.AccountStatus.RESTRICTED);
                    accountRepository.save(account);
                    System.out.println("Account with ID " + account.getAccountId() +
                            " has been set to RESTRICTED due to an overdue loan.");
                }
            }
        }
    }
}
