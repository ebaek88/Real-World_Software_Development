package com.ebaek88;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Objects;

// Domain class for a bank transaction
public class BankTransaction {
  private final LocalDate date;
  private final double amount;
  private final String description;


  public BankTransaction(final LocalDate date, final double amount, final String description) {
    this.date = date;
    this.amount = amount;
    this.description = description;
  }

  public LocalDate getDate() {
    return date;
  }

  public double getAmount() {
    return amount;
  }

  public String getDescription() {
    return description;
  }

  @Override
  public String toString() {
    return "BankTransaction{" +
        "date=" + date +
        ", amount=" + amount +
        ", description='" + description + '\'' +
        '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) { return true; }
    if (o == null || this.getClass() != o.getClass()) {
      return false;
    }

    BankTransaction that = (BankTransaction) o;
    return Double.compare(that.amount, this.amount) == 0 &&
            this.date.equals(that.date) &&
            this.description.equals(that.description);
  }

  @Override
  public int hashCode() {
    return Objects.hash(date, amount, description);
  }

  // method for collecting multiple errors in one pass
  public Notification validate() {

    final Notification notification = new Notification();

    if(this.description.length() > 100) {
      notification.addError("The description is too long");
    }

    try {
      if (this.date.isAfter(LocalDate.now())) {
        notification.addError("Date cannot be in the future");
      }
    }
    catch(DateTimeParseException e) {
      notification.addError("Invalid format for the date");
    }

    try {
      double amountForExceptionCheck = this.amount;
    }
    catch (NumberFormatException e) {
      notification.addError("Invalid format for the amount");
    }

    return notification;
  }//method
}
