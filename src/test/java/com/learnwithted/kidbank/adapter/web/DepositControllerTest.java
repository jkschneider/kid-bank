package com.learnwithted.kidbank.adapter.web;

import com.learnwithted.kidbank.domain.Account;
import com.learnwithted.kidbank.domain.DummyUserProfile;
import com.learnwithted.kidbank.domain.TestAccountBuilder;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.validation.BindingResult;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class DepositControllerTest {

  @Mock(stubOnly = true)
  private BindingResult mockBindingResult;

  @Test
  public void depositCommandShouldAddAmountToAccount() throws Exception {
    TransactionCommand depositCommand = TransactionCommand.createWithTodayDate();
    depositCommand.setAmount(BigDecimal.valueOf(12.34));

    Account account = TestAccountBuilder.builder().buildAsCore();

    DepositController depositController = new DepositController(account);

    Mockito.when(mockBindingResult.hasErrors()).thenReturn(false);
    depositController.processDepositCommand(depositCommand, mockBindingResult, new DummyUserProfile());

    assertThat(account.balance())
        .isEqualTo(1234);
  }

}
