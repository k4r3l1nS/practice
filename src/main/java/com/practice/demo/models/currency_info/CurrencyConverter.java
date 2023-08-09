package com.practice.demo.models.currency_info;

import com.practice.demo.service.CurrencyRatesService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class CurrencyConverter {

    private final CurrencyRatesService currencyRatesService;

    public BigDecimal convert(Currency currencyFrom, Currency currencyTo, BigDecimal sum) {

        return currencyRatesService.convert(currencyFrom, currencyTo, sum);
    }
}
