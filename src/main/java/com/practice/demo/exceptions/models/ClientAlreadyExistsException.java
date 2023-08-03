package com.practice.demo.exceptions.models;

public class ClientAlreadyExistsException extends RuntimeException {

    public ClientAlreadyExistsException(String message) {

        super(message);
    }
}
