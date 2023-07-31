package com.practice.demo.controllers;

import com.practice.demo.dto.AccountDto;
import com.practice.demo.dto.OperationListDto;
import com.practice.demo.models.db_views.AccountView;
import com.practice.demo.models.db_views.ClientView;
import com.practice.demo.models.db_views.OperationView;
import com.practice.demo.models.rates.Currency;
import com.practice.demo.models.rates.CurrencyRates;
import com.practice.demo.service.AccountService;
import com.practice.demo.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class AccountController {

    private final ClientService clientService;
    private final AccountService accountService;

    @GetMapping("/clients/{client_id}/account-{account_id}")
    public String accountById(@PathVariable(value = "client_id") Long clientId,
                              @PathVariable(value = "account_id") Long accountId, Model model) {

        OperationListDto operationListDto = accountService.findAllOperationsByAccountId(accountId);

        model.addAttribute("operation_list_dto", operationListDto);

        return "account-by-id";
    }

    @GetMapping("/clients/{id}/new-account")
    public String newAccount(@PathVariable(value="id") Long clientId, Model model) {

        model.addAttribute("client_id", clientId);
        model.addAttribute("currencies", Currency.values());
        model.addAttribute("accountDto", AccountDto.builder().build());

        return "new-account";
    }

    @PostMapping("/clients/{id}/new-account")
    public String addAccount(@PathVariable(value = "id") Long clientId, @ModelAttribute AccountDto accountDto) {

        accountService.addAccount(accountDto, clientId);

        return "redirect:/clients/{id}";
    }

    @GetMapping("/clients/{client_id}/account-{account_id}/edit")
    public String editAccount(@PathVariable(value = "client_id") Long clientId,
                              @PathVariable(value = "account_id") Long accountId, Model model) {

        var account = accountService.findAccountById(accountId);

        model.addAttribute("account", account);
        model.addAttribute("currencies", Currency.values());
        model.addAttribute("rates", new CurrencyRates().getRates());
        model.addAttribute("accountDto", AccountDto.builder().build());

        return "edit-account";
    }

    @PutMapping("/clients/{client_id}/account-{account_id}/edit")
    public String updateAccount(@PathVariable(value = "client_id") Long clientId,
                                @PathVariable(value = "account_id") Long accountId,
                                @ModelAttribute AccountDto accountDto) {

        accountService.updateAccount(accountDto, accountId);

        return "redirect:/clients/{client_id}";
    }

    @GetMapping("/clients/{client_id}/account-{account_id}/deactivate")
    public String deactivateAccount(@PathVariable(value = "client_id") Long clientId,
                                 @PathVariable(value = "account_id") Long accountId, Model model) {

        accountService.deactivateAccountById(accountId);

        return "redirect:/clients/{client_id}";
    }
}
