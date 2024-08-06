package com.ebaek88;

import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

public class BankStatementCSVParserTest {
  private final BankStatementParser statementParser = new BankStatementCSVParser();

  @Test
  public void shouldParseOneCorrectLine() throws Exception {
    final String line = "30-01-2017,-50,Tesco";

    final BankTransaction result = statementParser.parseFrom(line);

    final BankTransaction expected = new BankTransaction(LocalDate.of(2017, Month.JANUARY, 30), -50, "Tesco");
    final double tolerance = 0.0d;

    Assert.assertEquals(expected.getDate(), result.getDate());
    Assert.assertEquals(expected.getAmount(), result.getAmount(), tolerance);
    Assert.assertEquals(expected.getDescription(), result.getDescription());
  }

  @Test
  public void shouldParseAllCorrectLines() throws Exception {
    //Given
    final String line1 = "30-01-2017,-50,Tesco";
    final String line2 = "04-02-2017,-20,Pizza Hut";
    final String line3 = "17-01-2017,300,Money Transfer";
    final String line4 = "08-02-2017,-5,Bus";
    final List<String> bankTransactionStrings = new ArrayList<>();
    bankTransactionStrings.add(line1);
    bankTransactionStrings.add(line2);
    bankTransactionStrings.add(line3);
    bankTransactionStrings.add(line4);
    final List<BankTransaction> bankTransactions = statementParser.parseLinesFrom(bankTransactionStrings);

    //When
    final BankTransaction expected1 = new BankTransaction(LocalDate.of(2017, Month.JANUARY, 30), -50, "Tesco");
    final BankTransaction expected2 = new BankTransaction(LocalDate.of(2017, Month.FEBRUARY, 4), -20, "Pizza Hut");
    final BankTransaction expected3 = new BankTransaction(LocalDate.of(2017, Month.JANUARY, 17), 300, "Money Transfer");
    final BankTransaction expected4 = new BankTransaction(LocalDate.of(2017, Month.FEBRUARY, 8), -5, "Bus");
    final List<BankTransaction> expectedBankTransactions = new ArrayList<>();
    expectedBankTransactions.add(expected1);
    expectedBankTransactions.add(expected2);
    expectedBankTransactions.add(expected3);
    expectedBankTransactions.add(expected4);
    final double tolerance = 0.0;

    //Then
    for(int i = 0; i < bankTransactionStrings.size(); i++) {
      Assert.assertEquals(bankTransactions.get(i).getDate(), expectedBankTransactions.get(i).getDate());
      Assert.assertEquals(bankTransactions.get(i).getAmount(), expectedBankTransactions.get(i).getAmount(), tolerance);
      Assert.assertEquals(bankTransactions.get(i).getDescription(), expectedBankTransactions.get(i).getDescription());
    }
  }
}
