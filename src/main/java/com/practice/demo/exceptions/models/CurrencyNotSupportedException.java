package com.practice.demo.exceptions.models;

public class CurrencyNotSupportedException extends RuntimeException {

    public CurrencyNotSupportedException(String message) {

        super(message);
    }
}
