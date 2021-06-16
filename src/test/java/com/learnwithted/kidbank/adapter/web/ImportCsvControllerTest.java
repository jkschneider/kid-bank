package com.learnwithted.kidbank.adapter.web;

import com.learnwithted.kidbank.domain.Account;
import com.learnwithted.kidbank.domain.TestAccountBuilder;
import com.learnwithted.kidbank.domain.Transaction;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

public class ImportCsvControllerTest {
  @Test
  public void importCommandShouldLoadTransactionsIntoAccount() throws Exception {
    ImportDto importDto = new ImportDto();
    importDto.setContent("03/07/2018,Cash Deposit, $5.50 ,Bottle return\r\n" +
                                 "03/25/2018,Payment, $(12.00),MTG Draft Game Kastle\r\n" +
                                 "04/01/2018,Interest Credit, $0.11 ,Interest based on 2%/year\r\n");

    Account account = TestAccountBuilder.builder().buildAsCore();

    ImportCsvController importCsvController = new ImportCsvController(account);
    importCsvController.processImportCommand(importDto);

    assertThat(account.transactions())
        .usingElementComparatorIgnoringFields("id")
        .containsExactlyInAnyOrder(
            CsvImporter.createDeposit(LocalDateTime.of(2018, 3, 7, 0, 0),
                                      550, "Bottle return"),
            CsvImporter.createSpend(LocalDateTime.of(2018, 3, 25, 0, 0),
                                    1200, "MTG Draft Game Kastle"),
            Transaction.createInterestCredit(LocalDateTime.of(2018, 4, 1, 0, 0), 11));
  }

}
