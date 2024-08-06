package com.ebaek88;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Month;
import java.util.List;

public class BankStatementAnalyzer {

//  For the path variable RESOURCES, please set it to the appropriate directory where the csv file is located.
//  Also, when you execute the main class or the jar file, type "bankstatements.csv" as the argument
  private static final String RESOURCES = "./extra-resources/";
//  private static final BankStatementCSVParser bankStatementParser = new BankStatementCSVParser();  // now the parsing functionality is encapsulated in a different class

  public static void analyze (final String fileName, final BankStatementParser bankStatementParser) throws IOException {

    final Path path = Paths.get(RESOURCES + fileName);
    final List<String> lines = Files.readAllLines(path);

    final List<BankTransaction> bankTransactions = bankStatementParser.parseLinesFrom(lines);
    final BankStatementProcessor bankStatementProcessor = new BankStatementProcessor(bankTransactions);

    // Defining an anonymous inner class that implements the interface whenever the new filtering requirements pop up
    final List<BankTransaction> transactions1 = bankStatementProcessor.findTransactions(new BankTransactionFilter() {
      @Override
      public boolean test(BankTransaction bankTransaction) {
        return bankTransaction.getDate().getMonth() == Month.FEBRUARY
            && bankTransaction.getAmount() >= 1_000;
      }
    });

    // Or using a lambda expression, which simplifies the code greatly
    final List<BankTransaction> transactions2 = bankStatementProcessor.findTransactions(bankTransaction ->
        bankTransaction.getDate().getMonth() == Month.FEBRUARY
        && bankTransaction.getAmount() >= 1_000);

    collectSummary(bankStatementProcessor);
  }//ch02.main

  private static void collectSummary(final BankStatementProcessor bankStatementProcessor) {
    System.out.println("The total for all transactions is "
        + bankStatementProcessor.calculateTotalAmount());

    System.out.println("The total for transactions in January is "
        + bankStatementProcessor.calculateTotalInMonthStream(Month.JANUARY));

    System.out.println("The total for transactions in February is "
        + bankStatementProcessor.calculateTotalInMonthStream(Month.FEBRUARY));

    System.out.println("The total salary received is "
        + bankStatementProcessor.calculateTotalForCategory("Salary"));
  }//method

  // The class BankStatementAnalyzer puts all the different parts of the application together.
  // However, this is an example of poor cohesion because the concerns of calculations
  //  declared in this class are not directly related to parsing or reporting.
  // It is better to put aside the calculation operations into a separate class called BankStatementProcessor.

  // calculates the total amount of transactions in the bank statement
//  public static double calculateTotalAmount(final List<BankTransaction> bankTransactions) {
//    double total = 0.0;
//    for(final BankTransaction bankTransaction : bankTransactions) {
//      total += bankTransaction.getAmount();
//    }
//
//    return total;
//  }//method

  // lists the transactions in a particular month in the bank statement
//  public static List<BankTransaction> selectInMonth(final List<BankTransaction> bankTransactions, final Month month) {
//    final List<BankTransaction> bankTransactionsInMonth = new ArrayList<>();
//    for(final BankTransaction bankTransaction : bankTransactions) {
//      if (bankTransaction.getDate().getMonth() == month) {
//        bankTransactionsInMonth.add(bankTransaction);
//      }
//    }
//
//    return bankTransactionsInMonth;
//  }//method
}//class