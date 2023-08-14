package com.practice.demo.dto.entity_dto;

import com.practice.demo.exceptions.models.EmptyFieldException;
import com.practice.demo.exceptions.models.InvalidSumInputException;
import com.practice.demo.models.entities.Operation;
import com.practice.demo.models.currency_enum.Currency;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
public class OperationDto {

    private Operation.OperationKind operationKind;

    private BigDecimal transactionSum;

    private Currency currencyFrom;

    public Operation toEntity() {

        Operation operation = new Operation();

        operation.setTransactionSum(transactionSum);
        operation.setCurrencyFrom(currencyFrom);
        operation.setOperationKind(operationKind);

        return operation;
    }

    private boolean hasEmptyFields() {

        return operationKind == null || transactionSum == null || currencyFrom == null;
    }

    public void throwIfNotFilled() {

        if (hasEmptyFields()) {

            throw new EmptyFieldException("All fields and radio buttons must be filled in");
        }
    }

    public void throwIfInvalid() {

        if (transactionSum.compareTo(BigDecimal.ZERO) <= 0) {

            throw new InvalidSumInputException("Transaction sum must be above 0");
        }
    }
}
