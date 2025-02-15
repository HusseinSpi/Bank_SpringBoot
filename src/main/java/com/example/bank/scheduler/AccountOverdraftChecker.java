package com.example.bank.scheduler;

import com.example.bank.entity.Account;
import com.example.bank.entity.Card;
import com.example.bank.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
public class AccountOverdraftChecker {

    private final AccountRepository accountRepository;

    @Autowired
    public AccountOverdraftChecker(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }


    @Scheduled(cron = "0 0 0 20 * ?")
//    @Scheduled(cron = "0 13 15 * * *")
    public void checkOverdraftAccounts() {
        List<Account> accounts = accountRepository.findAllWithCards();
        for (Account account : accounts) {
            if (account.getBalance().compareTo(BigDecimal.ZERO) < 0) {
                System.out.println("Overdraft detected for account ID: " + account.getAccountId());
                System.out.println("account.getCards(): " + account.toString());

                if (account.getCards() != null) {
                    for (Card card : account.getCards()) {
                        System.out.println("card: " + card.toString());
                        if (card.getCardType() == Card.CardType.CREDIT) {
                            if (card.getCardStatus() != Card.CardStatus.BLOCKED) {
                                card.setCardStatus(Card.CardStatus.BLOCKED);
                                System.out.println("  Card ID " + card.getCardId() + " blocked (credit card).");
                            }
                        }
                    }
                }

                if (account.getStatus() != Account.AccountStatus.RESTRICTED) {
                    account.setStatus(Account.AccountStatus.RESTRICTED);
                    System.out.println("  Account ID " + account.getAccountId() + " set to RESTRICTED.");
                }

                accountRepository.save(account);
            }
        }
    }
}
