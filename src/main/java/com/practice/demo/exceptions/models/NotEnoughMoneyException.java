package com.practice.demo.exceptions.models;

public class NotEnoughMoneyException extends RuntimeException {

    public NotEnoughMoneyException(String message) {

        super(message);
    }
}
