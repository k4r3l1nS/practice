package com.practice.demo.exceptions.model;

public class UnsupportedCurrencyException extends RuntimeException {

    public UnsupportedCurrencyException(String message) {

        super(message);
    }
}
