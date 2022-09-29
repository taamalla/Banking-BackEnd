package tn.esprit.banking.service;

import tn.esprit.banking.entity.Transaction;
import tn.esprit.banking.enums.TypeTransaction;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface ITransactionService {

    Transaction save(Transaction transaction);

    Transaction findTransactionById(Integer id);

    List<Transaction> getTransactionByType(TypeTransaction transactionType);



    BigDecimal getAllNegativeBalance();

    List<Transaction> getMonthlyTransactions(Date date);

    List<Transaction> getMonthlyTransactionsByClient(Integer idClient, Date date);

    Transaction revertTransaction(Integer id);

    List<Transaction> getAllTransaction();

}
