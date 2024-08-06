package com.ebaek88;

import java.time.Month;
import java.util.ArrayList;
import java.util.List;

// Grouping the calculations operations in the class BankStatementProcessor
public class BankStatementProcessor {
  // The list of transactions method argument is
  //  shared for all these operations, so you can include it as a field to the class.
  // As a result,
  //  your method signatures become simpler to reason about and the class BankStatementProcessor is more cohesive.

  private final List<BankTransaction> bankTransactions; // class field for storing bank transactions from the parser

  public BankStatementProcessor(final List<BankTransaction> bankTransactions) {
    this.bankTransactions = bankTransactions;
  }//constructor

  // method to summarize transactions. How to summarize is implemented in accordance with the business logic.
  public double summarizeTransactions(final BankTransactionSummarizer bankTransactionSummarizer) {
    double result = 0;
    for(final BankTransaction bankTransaction : bankTransactions) {
      result = bankTransactionSummarizer.summarize(result, bankTransaction);
    }
    return result;
  }//method

  public double calculateTotalInMonth(final Month month) {
    return summarizeTransactions((acc, bankTransaction) ->
            bankTransaction.getDate().getMonth() == month ? acc + bankTransaction.getAmount() : acc);
  }//method

  // The same method as above but using Java Stream.
  public double calculateTotalInMonthStream(final Month month) {
    return bankTransactions.stream().filter(bankTransaction -> bankTransaction.getDate().getMonth() == month)
        .mapToDouble(BankTransaction::getAmount).sum();
  }

  public List<BankTransaction> findTransactions(final BankTransactionFilter bankTransactionFilter) {
    final List<BankTransaction> result = new ArrayList<>();
    for(final BankTransaction bankTransaction : bankTransactions) {
      if(bankTransactionFilter.test(bankTransaction)) {
        result.add(bankTransaction);
      }
    }
    return result;
  }//method

  public List<BankTransaction> findTransactionsGreaterThanEqual(final int amount) {
    return findTransactions(bankTransaction -> bankTransaction.getAmount() >= amount);
  }//method

  public double calculateTotalAmount() {
    double total = 0;
    for (final BankTransaction bankTransaction : bankTransactions) {
      total += bankTransaction.getAmount();
    }
    return total;
  }//method


  public double calculateTotalForCategory(final String category) {
    double total = 0;
    for(final BankTransaction bankTransaction : bankTransactions) {
      if (bankTransaction.getDescription().equals(category)) {
        total += bankTransaction.getAmount();
      }
    }
    return total;
  }//method

//  public List<BankTransaction> findTransactionsGreaterThanEqual(final int amount) {
//    final List<BankTransaction> result = new ArrayList<>();
//    for(final BankTransaction bankTransaction : bankTransactions) {
//      if(bankTransaction.getAmount() >= amount) {
//        result.add(bankTransaction);
//      }
//    }
//    return result;
//  }//method
//
//  public List<BankTransaction> findTransactionsInMonth(final Month month) {
//    final List<BankTransaction> result = new ArrayList<>();
//    for(final BankTransaction bankTransaction : bankTransactions) {
//      if(bankTransaction.getDate().getMonth() == month) {
//        result.add(bankTransaction);
//      }
//    }
//    return result;
//  }//method

}//class