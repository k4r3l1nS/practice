package com.practice.demo.exceptions.models;

public class EmptyFieldException extends RuntimeException {

    public EmptyFieldException(String message) {

        super(message);
    }
}
