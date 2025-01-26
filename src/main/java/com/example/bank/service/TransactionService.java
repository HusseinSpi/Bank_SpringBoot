package com.example.bank.service;

import com.example.bank.entity.Transaction;
import java.util.List;

public interface TransactionService {
    Transaction createTransaction(Transaction transaction);
    Transaction getTransactionById(Long id);
    List<Transaction> getAllTransactions();

    Transaction updateTransaction(Long id, Transaction updatedTransaction);

    void deleteTransaction(Long id);
}
