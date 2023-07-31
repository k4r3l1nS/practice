package com.practice.demo.controllers;

import com.practice.demo.dto.*;
import com.practice.demo.dto.paging_and_sotring.AccountPagingAndSortingDto;
import com.practice.demo.dto.paging_and_sotring.ClientPagingAndSortingDto;
import com.practice.demo.dto.specification.models.AccountSpecificationDto;
import com.practice.demo.dto.specification.models.ClientSpecificationDto;
import com.practice.demo.models.db_views.ClientView;
import com.practice.demo.models.rates.Currency;
import com.practice.demo.models.rates.CurrencyRates;
import com.practice.demo.service.AccountService;
import com.practice.demo.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;
    private final AccountService accountService;

    @GetMapping("/clients")
    public String clients(@ModelAttribute ClientPagingAndSortingDto pagingAndSortingDto,
                          @ModelAttribute ClientSpecificationDto clientSpecificationDto, Model model) {

        pagingAndSortingDto.fillEmptyFields();
        clientSpecificationDto.fillEmptyFields();

        model.addAttribute("PASdto", pagingAndSortingDto);
        model.addAttribute("CSdto", clientSpecificationDto);

        Page<ClientView> clients = clientService.fetchNextPage(pagingAndSortingDto, clientSpecificationDto);

        model.addAttribute("clients", clients);

        return "clients";
    }

    @GetMapping("/clients/{id}")
    public String clientById(@ModelAttribute AccountPagingAndSortingDto accountPagingAndSortingDto,
                             @ModelAttribute AccountSpecificationDto accountSpecificationDto,
                             @PathVariable(value = "id") Long clientId, Model model) {

        accountPagingAndSortingDto.fillEmptyFields();
        accountSpecificationDto.fillEmptyFields();

        model.addAttribute("PASdto", accountPagingAndSortingDto);
        model.addAttribute("ASdto", accountSpecificationDto);

        AccountListDto accountListDto = AccountListDto.valueFrom(
                accountService.fetchNextPageByClientId(accountPagingAndSortingDto, accountSpecificationDto, clientId),
                accountService.findOneAccountView(clientId));

        model.addAttribute("accountListDto", accountListDto);
        model.addAttribute("currencies", Currency.values());

        return "client-by-id";
    }

    @GetMapping("/clients/add")
    public String addClient(Model model) {

        model.addAttribute("clientDto", ClientDto.builder().build());

        return "new-client";
    }

    @PostMapping("/clients/add")
    public String addClient(@ModelAttribute ClientDto clientDto) {

        clientService.addClient(clientDto);

        return "redirect:/clients";
    }

    @GetMapping("/clients/{id}/edit")
    public String editClient(@PathVariable(value = "id") Long id, Model model) {

        ClientView client = clientService.findClientById(id);
        model.addAttribute("client", client);

        model.addAttribute("clientDto", ClientDto.builder().build());

        return "edit-client";
    }

    @PutMapping("/clients/{id}/edit")
    public String updateClient(@PathVariable(value = "id") Long id, @ModelAttribute ClientDto clientDto) {

        clientService.updateClient(clientDto, id);

        return "redirect:/clients";
    }

    @GetMapping("/clients/{id}/activate")
    public String activateClientById(@PathVariable(value = "id") Long id) {

        clientService.activateClientById(id);

        return "redirect:/clients";
    }

    @GetMapping("/clients/{id}/deactivate")
    public String deactivateClientById(@PathVariable(value = "id") Long id) {

        clientService.deactivateClientById(id);

        return "redirect:/clients";
    }
}
