package com.practice.demo.controllers;

import com.practice.demo.dto.entity_dto.CurrencyRatesDto;
import com.practice.demo.models.currency_info.Currency;
import com.practice.demo.models.entities.LastCurrencyRatesUpdate;
import com.practice.demo.service.CurrencyRatesService;
import com.practice.demo.uri_handler.UriHandler;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
@RequiredArgsConstructor
public class CurrencyRatesController {

    private final CurrencyRatesService currencyRatesService;

    @GetMapping("/currency-rates")
    public String currencyRates(@ModelAttribute CurrencyRatesDto currencyRatesDto,
                                HttpServletRequest httpServletRequest, Model model) {

        model.addAttribute("uriPairList", UriHandler.parse(httpServletRequest.getRequestURI()));

        model.addAttribute("CRSdto", currencyRatesDto);

        model.addAttribute("rate", currencyRatesService.findRate(currencyRatesDto));

        LastCurrencyRatesUpdate lastUpdate = currencyRatesService.getLastUpdate();

        model.addAttribute("currencies", Currency.values());
        model.addAttribute("lastUpdate", lastUpdate);

        return "currency-rates";
    }
}
