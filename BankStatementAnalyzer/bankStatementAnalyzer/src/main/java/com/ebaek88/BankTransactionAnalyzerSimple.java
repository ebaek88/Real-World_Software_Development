package com.ebaek88;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class BankTransactionAnalyzerSimple {
  private static final String RESOURCES = "src/ch02.main/resources/";

  public static void main(final String... args) throws IOException {  // using variable arguments for the ch02.main method
    final Path path = Paths.get(RESOURCES + args[0]);   // The CSV file is in the file path variable RESOURCES
    final List<String> lines = Files.readAllLines(path);        // Read all the lines of the CSV file and store each line in the list variable
    double total = 0d;  // same as double total = 0.0; '0d' is a relic of Oak, the predecessor of Java
    double janTotal = 0.0;  // Variable to store total amount of transactions in January
    final DateTimeFormatter DATE_PATTERN = DateTimeFormatter.ofPattern("dd-MM-yyyy"); // Creates a date formatter using the specified pattern in the parameter
    double totalJan = 0.0;  // total for all transactions in January
    for (final String line: lines) {
      final String[] columns = line.split(",");
      final double amount = Double.parseDouble(columns[1]);     // The amount changed is in the second column of the CSV file
      total += amount;
      final LocalDate date = LocalDate.parse(columns[0], DATE_PATTERN); // The first column contains the date of transaction
      if (date.getMonth() == Month.JANUARY) {   // Checks if the transaction was made in January
        final double janAmount = Double.parseDouble(columns[1]);
        janTotal += janAmount;
      }

    }

    System.out.println("The total for all transactions is " + total);
    System.out.println("The total for all transactions in January is " + janTotal);
  }//ch02.main
}//class
