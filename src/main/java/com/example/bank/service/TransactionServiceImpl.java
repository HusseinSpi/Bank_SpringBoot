package com.example.bank.service;

import com.example.bank.entity.Account;
import com.example.bank.entity.Transaction;
import com.example.bank.repository.AccountRepository;
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
    private final AccountRepository accountRepository;

    @Autowired
    public TransactionServiceImpl(TransactionRepository transactionRepository,
                                  AccountRepository accountRepository) {
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
    }
        //DONE
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

        Account destinationAccount = accountRepository.findByAccountNumber(transaction.getDestinationAccount().getAccountNumber())
                .orElseThrow(() -> new RuntimeException("Destination account not found with account number: " + transaction.getDestinationAccount().getAccountNumber()));
        Account sourAccount = null;
        if (Objects.equals(destinationAccount.getAccountNumber(), transaction.getDestinationAccount().getAccountNumber())) {

            switch (transactionType) {
                case Transaction.TransactionType.DEPOSIT:
                    destinationAccount.deposit(transaction.getAmount());
                    break;
                case Transaction.TransactionType.WITHDRAWAL:
                    destinationAccount.withdraw(transaction.getAmount());
                    break;
                case Transaction.TransactionType.TRANSFER:
                    // Initialize sourAccount only for TRANSFER
                    sourAccount = accountRepository.findByAccountNumber(transaction.getSourceAccount().getAccountNumber())
                            .orElseThrow(() -> new RuntimeException("Source account not found with account number: " + transaction.getSourceAccount().getAccountNumber()));

                    destinationAccount.deposit(transaction.getAmount());
                    System.out.println("sourAccount before withdrawal: " + sourAccount.toString());
                    sourAccount.withdraw(transaction.getAmount());
                    System.out.println("sourAccount after withdrawal: " + sourAccount.toString());
                    break;
                default:
                    throw new IllegalArgumentException("Unsupported transaction type: " + transactionType);
            }

            // Save the destination account after processing
            accountRepository.save(destinationAccount);

            // Create the new transaction
            Transaction newTransaction = new Transaction();
            newTransaction.setAmount(transaction.getAmount());
            newTransaction.setCurrency(transaction.getCurrency());
            newTransaction.setTransferMode(transferMode);
            newTransaction.setTransactionType(transactionType);
            newTransaction.setDestinationAccount(destinationAccount);
            newTransaction.setTimestamp(LocalDateTime.now());
            newTransaction.setDescription(transaction.getDescription());

            // If it's a TRANSFER, handle the source account
            if (transactionType == Transaction.TransactionType.TRANSFER && sourAccount != null) {
                accountRepository.save(sourAccount); // Save the updated source account
                newTransaction.setSourceAccount(sourAccount); // Set the source account in the transaction
            }

            // Save and return the new transaction
            return transactionRepository.save(newTransaction);
        }

// If destination account doesn't match, return null or handle accordingly
        return null;
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
