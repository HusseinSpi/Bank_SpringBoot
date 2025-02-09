package com.example.bank.service;

import com.example.bank.entity.Account;
import com.example.bank.entity.Card;
import com.example.bank.entity.Transaction;
import com.example.bank.repository.AccountRepository;
import com.example.bank.repository.CardRepository;
import com.example.bank.repository.TransactionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;
    private final CardRepository cardRepository;
    private final AccountRepository accountRepository;

    @Autowired
    public TransactionServiceImpl(TransactionRepository transactionRepository, CardRepository cardRepository,
                                  AccountRepository accountRepository) {
        this.transactionRepository = transactionRepository;
        this.cardRepository = cardRepository;
        this.accountRepository = accountRepository;
    }

    @Override
    public Transaction createTransaction(Transaction transaction) {
        Transaction.TransactionType transactionType;
        Transaction.TransferMode transferMode;

        try {
            transactionType = transaction.getTransactionType();
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid transaction type: " + transaction.getTransactionType());
        }

        try {
            transferMode = transaction.getTransferMode();
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid transfer mode: " + transaction.getTransferMode());
        }
        Account destinationAccount=null;
        System.out.println(transaction.getCard());
        Card destinationCard = null;
        if (transaction.getCard() != null) {
            destinationCard = cardRepository.findByCardNumber(
                    transaction.getCard().getCardNumber(),
                    transaction.getCard().getPassword()
            ).orElseThrow(() ->
                    new RuntimeException("Destination Card not found with card number: "
                            + transaction.getCard().getCardNumber() + " and password "
                            + transaction.getCard().getPassword())
            );
            destinationAccount = destinationCard.getAccount();
        }
        else {

        destinationAccount = accountRepository.findByAccountNumber(transaction.getDestinationAccount().getAccountNumber())
                .orElseThrow(() -> new RuntimeException("Destination account not found with account number: " + transaction.getDestinationAccount().getAccountNumber()));
        }
        Account sourAccount = null;

            switch (transactionType) {
                case Transaction.TransactionType.DEPOSIT:
                    destinationAccount.deposit(transaction.getAmount());
                    break;
                case Transaction.TransactionType.WITHDRAWAL:
                    destinationAccount.withdraw(transaction.getAmount(),destinationCard);
                    break;
                case Transaction.TransactionType.TRANSFER:
                    sourAccount = accountRepository.findByAccountNumber(transaction.getSourceAccount().getAccountNumber())
                            .orElseThrow(() -> new RuntimeException("Source account not found with account number: " + transaction.getSourceAccount().getAccountNumber()));

                    destinationAccount.deposit(transaction.getAmount());
                    System.out.println("sourAccount before withdrawal: " + sourAccount.toString());
                    sourAccount.withdraw(transaction.getAmount(),destinationCard);
                    System.out.println("sourAccount after withdrawal: " + sourAccount.toString());
                    break;
                default:
                    throw new IllegalArgumentException("Unsupported transaction type: " + transactionType);
            }

            accountRepository.save(destinationAccount);

            Transaction newTransaction = new Transaction();
            newTransaction.setAmount(transaction.getAmount());
            newTransaction.setCurrency(transaction.getCurrency());
            newTransaction.setTransferMode(transferMode);
            newTransaction.setTransactionType(transactionType);
            newTransaction.setDestinationAccount(destinationAccount);
            newTransaction.setTimestamp(LocalDateTime.now());
            newTransaction.setDescription(transaction.getDescription());

            if (transactionType == Transaction.TransactionType.TRANSFER && sourAccount != null) {
                accountRepository.save(sourAccount);
                newTransaction.setSourceAccount(sourAccount);
            }

            return transactionRepository.save(newTransaction);
    }


    @Override
    public Transaction getTransactionById(Long id) {
        return transactionRepository.findById(id).orElse(null);
    }

    @Override
    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    @Override
    public Transaction updateTransaction(Long id, Transaction updatedTransaction) {
        Transaction existingTransaction = transactionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transaction not found"));

        existingTransaction.setAmount(updatedTransaction.getAmount());
        existingTransaction.setCurrency(updatedTransaction.getCurrency());
        existingTransaction.setTimestamp(LocalDateTime.now());
        existingTransaction.setTransferMode(updatedTransaction.getTransferMode());
        existingTransaction.setTransactionType(updatedTransaction.getTransactionType());
        existingTransaction.setDestinationAccount(updatedTransaction.getDestinationAccount());
        existingTransaction.setSourceAccount(updatedTransaction.getSourceAccount());
        existingTransaction.setCard(updatedTransaction.getCard());

        return transactionRepository.save(existingTransaction);
    }

    @Override
    public void deleteTransaction(Long id) {
        transactionRepository.deleteById(id);
    }
}
