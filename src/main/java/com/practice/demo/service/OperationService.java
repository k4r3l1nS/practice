package com.practice.demo.service;

import com.practice.demo.dto.OperationDto;
import com.practice.demo.dto.OperationListDto;
import com.practice.demo.exceptions.model.EmptyRadioValueException;
import com.practice.demo.exceptions.model.InvalidSumInputException;
import com.practice.demo.models.Operation;
import com.practice.demo.models.db_views.OperationView;
import com.practice.demo.repos.AccountRepository;
import com.practice.demo.repos.ClientRepository;
import com.practice.demo.repos.OperationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class OperationService {

    private final AccountRepository accountRepository;
    private final OperationRepository operationRepository;

    public void addOperation(OperationDto operationDto, Long accountId)
            throws InvalidSumInputException, EmptyRadioValueException {

        if (operationDto.getOperationType() == null || operationDto.getCurrencyFrom() == null) {

            throw new EmptyRadioValueException("All fields and radio buttons must be filled in");
        }

        if (operationDto.getSum() == null || operationDto.getSum() <= 0) {

            throw new InvalidSumInputException("Sum must be entered and be above 0");
        }

        var account = accountRepository.findById(accountId).orElseThrow();

        var currencyTo = account.getCurrency();

        var operation = operationDto.toEntity(currencyTo);
        operationRepository.save(operation);

        account.addOperation(operation);
    }

    public Operation findById(Long operationId) {

        return operationRepository.findById(operationId).orElseThrow();
    }

    public OperationView findOperationById(Long operationId) {

        return operationRepository.findOperationById(operationId);
    }
}
