package com.practice.demo.dto.entity_dto;

import com.practice.demo.models.entities.Operation;
import com.practice.demo.models.currency_info.Currency;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class OperationDto {

    private Operation.OperationKind operationKind;

    private Double transactionSum;

    private Currency currencyFrom;

    public Operation toEntity() {

        Operation operation = new Operation();

        operation.setTransactionSum(transactionSum);
        operation.setCurrencyFrom(currencyFrom);
        operation.setOperationKind(operationKind);

        return operation;
    }
}
