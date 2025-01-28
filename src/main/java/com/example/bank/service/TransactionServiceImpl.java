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

        Account destinationAccount = accountRepository.findById(transaction.getDestinationAccount().getAccountId())
                .orElseThrow(() -> new RuntimeException("Destination account not found with ID: " + transaction.getDestinationAccount()));
        if(Objects.equals(destinationAccount.getAccountNumber(), transaction.getDestinationAccount().getAccountNumber())){

         switch (transactionType){
             case Transaction.TransactionType.DEPOSIT:
                destinationAccount.deposit(transaction.getAmount());

                 break;
             case Transaction.TransactionType.WITHDRAWAL:
                destinationAccount.withdraw(transaction.getAmount());
                 break;
             case Transaction.TransactionType.TRANSFER:
                 Account sourAccount = accountRepository.findById(transaction.getSourceAccount().getAccountId())
                         .orElseThrow(() -> new RuntimeException("Source account not found with ID: " + transaction.getSourceAccount()));
                destinationAccount.deposit(transaction.getAmount());
                 System.out.println("sourAccount.toString()"+sourAccount.toString());
                sourAccount.withdraw(transaction.getAmount());
                 System.out.println("sourAccount.toString()"+sourAccount.toString());

                 break;

        }
        accountRepository.save(destinationAccount);

        Transaction newtransaction = new Transaction();
        newtransaction.setAmount(transaction.getAmount());
        newtransaction.setCurrency(transaction.getCurrency());
        newtransaction.setTransferMode(transferMode);
        newtransaction.setTransactionType(transactionType);
        newtransaction.setDestinationAccount(transaction.getDestinationAccount());
        newtransaction.setTimestamp(LocalDateTime.now());
        newtransaction.setDescription(transaction.getDescription());
        newtransaction.setTimestamp(LocalDateTime.now());
        return transactionRepository.save(newtransaction);
    }
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
