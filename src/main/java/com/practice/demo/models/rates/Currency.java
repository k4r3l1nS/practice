package com.practice.demo.models.rates;

import lombok.Getter;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
@Getter
public enum Currency {

    RUB("RUB"),
    USD("USD"),
    EUR("EUR"),
    CNY("CNY");

    private final static Map<String, Currency> _map;

    static {

        _map = Stream.of(values()).collect(Collectors.toMap(Currency::getName, Function.identity()));
    }

    private final String name;

    Currency(String name) {
        this.name = name;
    }

    public static Currency resolveByName(String name) {
        return _map.getOrDefault(name, null);
    }

}
