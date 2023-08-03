package com.practice.demo.exceptions.models;

public class UnsupportedCurrencyException extends RuntimeException {

    public UnsupportedCurrencyException(String message) {

        super(message);
    }
}
