package com.learnwithted.kidbank.adapter.web;

import com.learnwithted.kidbank.adapter.DateFormatting;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

public class TransactionViewDateFormatTest {

  @Test
  public void stringFormattedDateShouldBeMMDDYYYY() throws Exception {
    LocalDateTime dateTime = LocalDateTime.of(2017, 3, 7, 0, 0);

    assertThat(DateFormatting.formatAsDate(dateTime))
        .isEqualTo("03/07/2017");
  }

}
