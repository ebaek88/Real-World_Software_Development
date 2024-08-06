package com.ebaek88;

import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDate;
import java.time.Month;

public class BankTransactionTest {

  @Test
  public void objectCreatedProperly() {
    //Given
    final LocalDate testDate = LocalDate.of(2024, Month.JULY, 21);
    final double testAmount = 999.99;
    final String testDescription = "Subway";
    final double tolerance = 0.0;

    //When
    final BankTransaction testObject = new BankTransaction(testDate, testAmount, testDescription);

    //Then
    Assert.assertNotNull(testObject);
    Assert.assertEquals(testObject.getDate(), testDate);
    Assert.assertEquals(testObject.getAmount(), testAmount, tolerance);
    Assert.assertEquals(testObject.getDescription(), testDescription);
  }
}
