package com.practice.demo.exceptions.controllers;

import com.practice.demo.exceptions.models.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ErrorInformation> catchAccountNameAlreadyTakenException(AccountNameAlreadyTakenException ex) {

        return new ResponseEntity<>(new ErrorInformation(HttpStatus.BAD_REQUEST.value(), ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorInformation> catchUndefinedClientException(UndefinedClientException ex) {

        return new ResponseEntity<>(new ErrorInformation(HttpStatus.BAD_REQUEST.value(), ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorInformation> catchUnsupportedCurrencyException(UnsupportedCurrencyException ex) {

        return new ResponseEntity<>(new ErrorInformation(HttpStatus.BAD_REQUEST.value(), ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorInformation> catchClientAlreadyExistsException(ClientAlreadyExistsException ex) {

        return new ResponseEntity<>(new ErrorInformation(HttpStatus.BAD_REQUEST.value(), ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorInformation> catchNotEnoughMoneyException(NotEnoughMoneyException ex) {

        return new ResponseEntity<>(new ErrorInformation(HttpStatus.BAD_REQUEST.value(), ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorInformation> catchInvalidSumInputException(InvalidSumInputException ex) {

        return new ResponseEntity<>(new ErrorInformation(HttpStatus.BAD_REQUEST.value(), ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorInformation> catchEmptyRadioValueException(EmptyFieldException ex) {

        return new ResponseEntity<>(new ErrorInformation(HttpStatus.BAD_REQUEST.value(), ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorInformation> catchResourceNotFoundException(ResourceNotFoundException ex) {

        return new ResponseEntity<>(new ErrorInformation(HttpStatus.NOT_FOUND.value(), ex.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorInformation> catchForbiddenResourceException(ForbiddenResourceException ex) {

        return new ResponseEntity<>(new ErrorInformation(HttpStatus.FORBIDDEN.value(), ex.getMessage()), HttpStatus.FORBIDDEN);
    }
}
