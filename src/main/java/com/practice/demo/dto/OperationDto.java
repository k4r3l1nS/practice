package com.practice.demo.dto;

import com.practice.demo.models.Operation;
import com.practice.demo.models.rates.Currency;
import com.practice.demo.models.rates.CurrencyRates;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class OperationDto {

    private String operationType;

    private Double sum;

    private String currencyFrom;

    public Operation toEntity(Currency currencyTo) {

        double entityCurrencySum = new CurrencyRates().convert(Currency.resolveByName(currencyFrom), currencyTo, sum);

        if (operationType.equals("Deposit")) {

            return Operation.getDeposit(entityCurrencySum);
        }

        return Operation.getWithdrawal(entityCurrencySum);
    }
}
