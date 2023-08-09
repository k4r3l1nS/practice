package com.practice.demo.controllers;

import com.practice.demo.models.entities.CurrencyRates;
import com.practice.demo.models.entities.LastCurrencyRatesUpdate;
import com.practice.demo.service.CurrencyRatesService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class CurrencyRatesController {

    private final CurrencyRatesService currencyRatesService;

    @GetMapping("/currency-rates")
    public String currencyRates(Model model) {

        List<CurrencyRates> currencyRatesList = currencyRatesService.findAll();
        LastCurrencyRatesUpdate lastUpdate = currencyRatesService.getLastUpdate();

        model.addAttribute("rates", currencyRatesList);
        model.addAttribute("lastUpdate", lastUpdate);

        return "currency-rates";
    }
}
