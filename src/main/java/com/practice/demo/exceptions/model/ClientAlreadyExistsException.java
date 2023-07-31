package com.practice.demo.exceptions.model;

public class ClientAlreadyExistsException extends RuntimeException {

    public ClientAlreadyExistsException(String message) {

        super(message);
    }
}
