package com.practice.demo.components.units;

import com.practice.demo.exceptions.models.CurrencyNotSupportedException;
import com.practice.demo.models.currency_enum.Currency;
import com.practice.demo.service.CurrencyRatesService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Collection;

@Component
@RequiredArgsConstructor
public class CurrencyUnit {

    private final CurrencyRatesService currencyRatesService;

    public BigDecimal convert(Currency currencyFrom, Currency currencyTo, BigDecimal sum) {

        return currencyRatesService.convert(currencyFrom, currencyTo, sum);
    }

    public boolean isCorrect(String currencyName) {

        return currencyRatesService.existsByCharCode(currencyName);
    }

    public boolean isCorrect(Collection<String> currencyNames) {

        for (var currencyName : currencyNames) {

            if (!currencyRatesService.existsByCharCode(currencyName)) {

                return false;
            }
        }

        return true;
    }

    public void throwIfNotSupported(String currency) {

        if (!isCorrect(currency)) {

            throw new CurrencyNotSupportedException("Currency with name " +
                    currency + " is not supported");
        }
    }
}
