package com.learnwithted.kidbank.adapter.web;

import com.learnwithted.kidbank.adapter.ScaledDecimals;
import com.learnwithted.kidbank.domain.Account;
import com.learnwithted.kidbank.domain.Transaction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/import")
public class ImportCsvController {
  private final Account account;


  public ImportCsvController(Account account) {
    this.account = account;
  }

  @GetMapping
  public String importCsvForm(Model model) {
    int balance = account.balance();
    model.addAttribute("balance", ScaledDecimals.formatAsMoney(balance));

    model.addAttribute("import", new ImportDto());

    return "import-csv";
  }

  @PostMapping
  public String processImportCommand(ImportDto importDto) {
    List<Transaction> transactions = new CsvImporter().importFrom(importDto.asLines());
    account.load(transactions);
    return "redirect:" + AccountController.ACCOUNT_URL;
  }

}
