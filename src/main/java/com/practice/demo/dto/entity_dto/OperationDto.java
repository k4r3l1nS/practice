package com.practice.demo.dto.entity_dto;

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

    public boolean hasEmptyFields() {

        return operationKind == null || transactionSum == null || currencyFrom == null;
    }
}
