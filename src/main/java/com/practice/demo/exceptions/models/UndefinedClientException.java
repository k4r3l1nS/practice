package com.practice.demo.exceptions.models;

public class UndefinedClientException extends RuntimeException {

    public UndefinedClientException(String message) {

        super(message);
    }
}
