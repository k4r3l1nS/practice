package com.practice.demo.service;

import com.practice.demo.models.currency_info.Currency;
import com.practice.demo.models.entities.CurrencyRates;
import com.practice.demo.models.entities.LastCurrencyRatesUpdate;
import com.practice.demo.repos.entity_repos.CurrencyRatesRepository;
import com.practice.demo.repos.entity_repos.LastCurrencyRatesUpdateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CurrencyRatesService {

    private final CurrencyRatesRepository currencyRatesRepository;
    private final LastCurrencyRatesUpdateRepository lastCurrencyRatesUpdateRepository;

    public BigDecimal convert(Currency currencyFrom, Currency currencyTo, BigDecimal sum) {

        var coefficient = currencyRatesRepository.findByCharCode(currencyFrom.getName()).getValue()
                .divide(currencyRatesRepository.findByCharCode(currencyTo.getName()).getValue(),
                        10, RoundingMode.FLOOR);

        return coefficient.multiply(sum);
    }

    public List<CurrencyRates> findAll() {

        return currencyRatesRepository.findAll();
    }

    public LastCurrencyRatesUpdate getLastUpdate() {

        return lastCurrencyRatesUpdateRepository.findAll().get(0);
    }
}
