package com.practice.demo.exceptions.models;

public class ForbiddenResourceException extends RuntimeException {

    public ForbiddenResourceException(String message) {

        super(message);
    }
}
