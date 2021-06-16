package com.learnwithted.kidbank.adapter.sms;

import com.learnwithted.kidbank.domain.*;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SpendCommandTransactionTest {

  @Test
  public void spendWithValidAmountShouldReduceAccountBalance() throws Exception {
    Account account = TestAccountBuilder.builder()
                                        .initialBalanceOf(100_00)
                                        .buildAsCore();

    SpendCommand spendCommand = new SpendCommand(account, 13_75, "Stuff");

    assertThat(spendCommand.execute(new UserProfile(null, null, null, Role.PARENT)))
        .isEqualTo("Spent $13.75, current balance is now $86.25");

    assertThat(account.balance())
        .isEqualTo(86_25);
  }

  @Test
  public void spendWithMultipleWordDescriptionCreatesSpendCommandWithDescription() throws Exception {
    TestAccountBuilder builder = TestAccountBuilder.builder();
    Account account = builder
                          .initialBalanceOf(100_00)
                          .buildAsCore();

    SpendCommand spendCommand = new SpendCommand(account, 13_75, "Cookie from store");

    spendCommand.execute(new UserProfile(null, null, null, Role.PARENT));

    assertThat(builder.transactionRepository().findAll().get(1))
        .extracting(Transaction::source)
        .isEqualTo("Cookie from store");

  }

}
