package com.learnwithted.kidbank.adapter.web;

import com.learnwithted.kidbank.adapter.ScaledDecimals;
import com.learnwithted.kidbank.domain.Account;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

import static com.learnwithted.kidbank.adapter.web.AccountController.ACCOUNT_URL;

@Controller
@RequestMapping(ACCOUNT_URL)
public class AccountController {

  static final String ACCOUNT_URL = "/account";
  private final Account account;


  public AccountController(Account account) {
    this.account = account;
  }

  @GetMapping()
  public String viewBalance(Model model, final Principal principal) {
    if (principal == null) {
      return "redirect:/logout";
    }

    int balance = account.balance();
    model.addAttribute("balance", ScaledDecimals.formatAsMoney(balance));
    model.addAttribute("transactions", TransactionView.viewsOf(account.transactions()));

    return "account-balance";
  }

}
