package com.practice.demo.exceptions.model;

public class AccountNameAlreadyTakenException extends RuntimeException {

    public AccountNameAlreadyTakenException(String message) {

        super(message);
    }
}
