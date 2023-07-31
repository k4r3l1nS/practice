package com.practice.demo.exceptions.model;

public class UndefinedClientException extends RuntimeException {

    public UndefinedClientException(String message) {

        super(message);
    }
}
