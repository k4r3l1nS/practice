package com.practice.demo.exceptions.model;

public class NotEnoughMoneyException extends RuntimeException {

    public NotEnoughMoneyException(String message) {

        super(message);
    }
}
