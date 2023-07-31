package com.practice.demo.models.rates;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CurrencyRates {

    //0 - RUB, 1 - USD, 2 - EUR, 3 - CNY

    /*public static final Double RUB_TO_USD = 0.0112827;
    public static final Double RUB_TO_EUR = 0.0102432;
    public static final Double RUB_TO_CNY = 0.0795689;
    public static final Double USD_TO_RUB = 1 / RUB_TO_USD;
    public static final Double USD_TO_EUR = RUB_TO_EUR / RUB_TO_USD;
    public static final Double USD_TO_CNY = RUB_TO_CNY / RUB_TO_USD;
    public static final Double EUR_TO_RUB = 1 / RUB_TO_EUR;
    public static final Double EUR_TO_USD = RUB_TO_USD / RUB_TO_EUR;
    public static final Double EUR_TO_CNY = RUB_TO_CNY / RUB_TO_EUR;
    public static final Double CNY_TO_RUB = 1 / RUB_TO_CNY;
    public static final Double CNY_TO_USD = RUB_TO_USD / RUB_TO_CNY;
    public static final Double CNY_TO_EUR = RUB_TO_EUR / RUB_TO_CNY;*/

    private static int NUMBER_OF_SUPPORTED_CURRENCIES = 4;

    public double[][] rates;

    public CurrencyRates() {

        rates = new double[NUMBER_OF_SUPPORTED_CURRENCIES][NUMBER_OF_SUPPORTED_CURRENCIES];

        for (int i = 0; i < NUMBER_OF_SUPPORTED_CURRENCIES; ++i) {

            rates[i][i] = 1;
        }
        rates[0][1] = 0.0112827;
        rates[0][2] = 0.0102432;
        rates[0][3] = 0.0795689;

        rates[1][2] = rates[0][2] / rates[0][1];
        rates[1][3] = rates[0][3] / rates[0][1];
        rates[2][3] = rates[0][3] / rates[0][2];

        for (int i = 1; i < NUMBER_OF_SUPPORTED_CURRENCIES; ++i) {

            for (int j = 0; j < i; ++j) {

                rates[i][j] = 1 / rates[j][i];
            }
        }
    }

    public double convert(Currency currencyFrom, Currency currencyTo, double sum) {

        return sum * rates[currencyFrom.ordinal()][currencyTo.ordinal()];
    }
}
