package com.practice.demo.controllers;

import com.practice.demo.dto.OperationDto;
import com.practice.demo.models.Account;
import com.practice.demo.models.Client;
import com.practice.demo.models.Operation;
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
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

@Controller
@RequiredArgsConstructor
public class OperationController {

    private final OperationService operationService;

    @GetMapping("/clients/{client_id}/account-{account_id}/operation-{operation_id}")
    public String operationById(@PathVariable(value="client_id") Long clientId,
                                @PathVariable(value="account_id") Long accountId,
                                @PathVariable(value="operation_id") Long operationId, Model model) {

        OperationView operation = operationService.findOperationById(operationId);

        model.addAttribute("operation", operation);

        return "operation-by-id";
    }

    @GetMapping("/clients/{client_id}/account-{account_id}/new-operation")
    public String performOperation(@PathVariable(value = "client_id") Long clientId,
                                   @PathVariable(value = "account_id") Long accountId, Model model) {

        model.addAttribute("client_id", clientId);
        model.addAttribute("account_id", accountId);
        model.addAttribute("currencies", Currency.values());
        model.addAttribute("operationKinds", Operation.OperationKind.values());
        model.addAttribute("operationDto", OperationDto.builder().build());

        return "new-operation";
    }

    @PostMapping("/clients/{client_id}/account-{account_id}/new-operation")
    public String addOperation(@PathVariable(value = "client_id") Long clientId,
                               @PathVariable(value = "account_id") Long accountId, @ModelAttribute OperationDto operationDto) {

        operationService.addOperation(operationDto, accountId);

        return "redirect:/clients/{client_id}/account-{account_id}";
    }
}
