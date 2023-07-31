package com.practice.demo.controllers;

import com.practice.demo.dto.AccountDto;
import com.practice.demo.dto.OperationListDto;
import com.practice.demo.dto.paging_and_sotring.ClientPagingAndSortingDto;
import com.practice.demo.dto.paging_and_sotring.OperationPagingAndSortingDto;
import com.practice.demo.dto.specification.models.ClientSpecificationDto;
import com.practice.demo.dto.specification.models.OperationSpecificationDto;
import com.practice.demo.models.db_views.AccountView;
import com.practice.demo.models.db_views.ClientView;
import com.practice.demo.models.db_views.OperationView;
import com.practice.demo.models.rates.Currency;
import com.practice.demo.models.rates.CurrencyRates;
import com.practice.demo.service.AccountService;
import com.practice.demo.service.ClientService;
import com.practice.demo.service.OperationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;
    private final OperationService operationService;

    @GetMapping("/clients/{client_id}/account-{account_id}")
    public String accountById(@PathVariable(value = "client_id") Long clientId,
                              @PathVariable(value = "account_id") Long accountId,
                              @ModelAttribute OperationPagingAndSortingDto pagingAndSortingDto,
                              @ModelAttribute OperationSpecificationDto operationSpecificationDto, Model model) {

        pagingAndSortingDto.fillEmptyFields();
        operationSpecificationDto.fillEmptyFields();

        model.addAttribute("PASdto", pagingAndSortingDto);
        model.addAttribute("OSdto", operationSpecificationDto);

        OperationListDto operationListDto = OperationListDto.valueFrom(
                operationService.fetchNextPageByAccountId(pagingAndSortingDto, operationSpecificationDto, accountId),
                operationService.findOneOperationView(accountId));

        model.addAttribute("operationListDto", operationListDto);

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
