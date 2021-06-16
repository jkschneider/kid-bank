package com.learnwithted.kidbank.adapter.sms;

import com.learnwithted.kidbank.domain.Account;
import com.learnwithted.kidbank.domain.Role;
import com.learnwithted.kidbank.domain.TestAccountBuilder;
import com.learnwithted.kidbank.domain.UserProfile;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CommandRoleTest {

  @Test
  public void parentRoleShouldBeAllowedToExecuteDepositCommand() throws Exception {
    Account account = TestAccountBuilder.builder().buildAsCore();

    DepositCommand depositCommand = new DepositCommand(account, 25_00);

    depositCommand.execute(new UserProfile(null, null, null, Role.PARENT));

    assertThat(account.balance())
        .isEqualTo(25_00);
  }

  @Test
  public void kidRoleShouldNotBeAllowedToExecuteDepositCommand() throws Exception {
    Account account = TestAccountBuilder.builder().buildAsCore();

    DepositCommand depositCommand = new DepositCommand(account, 25_00);

    depositCommand.execute(new UserProfile(null, null, null, Role.KID));

    assertThat(account.balance())
        .isZero();
  }

}
