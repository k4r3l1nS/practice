package com.practice.demo.exceptions.model;

public class EmptyFieldException extends RuntimeException {

    public EmptyFieldException(String message) {

        super(message);
    }
}
