package com.learnwithted.kidbank.domain;

import org.junit.jupiter.api.Test;

import static java.time.LocalDateTime.now;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class BalanceChangeNotificationTest {
  @Test
  public void depositShouldCauseNotificationOfBalanceChange() throws Exception {
    BalanceChangedNotifier mockNotifier = mock(BalanceChangedNotifier.class);

    // Given an account
    Account account = TestAccountBuilder.builder()
                                        .notifier(mockNotifier)
                                        .buildAsCore();

    // When we deposit money
    account.deposit(now(), 25_00, "test", new DummyUserProfile());

    // Then we expect the notification to be sent
    verify(mockNotifier).balanceChanged(25_00, 25_00);
  }

  @Test
  public void spendShouldCauseNotificationOfBalanceChange() throws Exception {
    BalanceChangedNotifier mockNotifier = mock(BalanceChangedNotifier.class);

    Account account = TestAccountBuilder.builder()
                                        .initialBalanceOf(100_00)
                                        .notifier(mockNotifier)
                                        .buildAsCore();

    account.spend(now(), 35_35, "test", new DummyUserProfile());

    verify(mockNotifier).balanceChanged(-35_35, 64_65);
  }
}
