package com.practice.demo.controllers;

import com.practice.demo.models.currency_info.Currency;
import com.practice.demo.models.currency_info.CurrencyRates;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CurrencyRatesController {

    @GetMapping("/currency-rates")
    public String currencyRates(Model model) {

        CurrencyRates currencyRates = new CurrencyRates();
        double[][] rates = currencyRates.getRates();

        model.addAttribute("rates", rates);
        model.addAttribute("values", Currency.values());

        return "currency-rates";
    }
}
