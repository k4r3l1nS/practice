package com.practice.demo.dto.specification_dto.models;

import com.practice.demo.dto.specification_dto.SpecificationDto;
import com.practice.demo.models.entities.Operation;
import com.practice.demo.models.currency_info.Currency;
import com.practice.demo.models.specification.Condition;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.*;

@Getter
@Setter
public class OperationSpecificationDto implements SpecificationDto {

    public final static String DEFAULT_OPERATION_DATE_TIME_OPERATION_TYPE = ">";
    public final static String DEFAULT_OPERATION_KIND_OPERATION_TYPE = "=";
    public final static String DEFAULT_TRANSACTION_SUM_OPERATION_TYPE = ">";
    public final static String DEFAULT_CURRENCY_FROM_OPERATION_TYPE = "=";


    private LocalDateTime operationDateTime;
    private Operation.OperationKind operationKind;
    private Double transactionSum;
    private Currency currencyFrom;

    private String operationDateTimeOT;
    private String operationKindOT;
    private String transactionSumOT;
    private String currencyFromOT;

    @Override
    public List<Condition> toConditions() {

        List<Condition> conditions = new ArrayList<>();

        if (operationDateTime != null) {

            conditions.add(new Condition("operationDateTime", Arrays.asList(operationDateTime),
                    Condition.OperationType.resolveByName(operationDateTimeOT),
                    Condition.LogicalOperatorType.AND));
        }

        if (operationKind != null) {

            conditions.add(new Condition("operationKind", Arrays.asList(operationKind),
                    Condition.OperationType.resolveByName(operationKindOT),
                    Condition.LogicalOperatorType.AND));
        }

        if (transactionSum != null) {

            conditions.add(new Condition("transactionSum", Arrays.asList(transactionSum),
                    Condition.OperationType.resolveByName(transactionSumOT),
                    Condition.LogicalOperatorType.AND));
        }

        if (currencyFrom != null) {

            conditions.add(new Condition("operationDateTime", Arrays.asList(currencyFrom),
                    Condition.OperationType.resolveByName(currencyFromOT),
                    Condition.LogicalOperatorType.AND));
        }

        if (!conditions.isEmpty())
            conditions.get(conditions.size() - 1).setLogicalOperator(Condition.LogicalOperatorType.END);

        return conditions;
    }

    public List<Condition> toConditions(Long accountId) {

        List<Condition> conditions = new ArrayList<>();

        conditions.add(0, new Condition("accountId", Arrays.asList(accountId.toString()), Condition.OperationType.EQUALS,
                Condition.LogicalOperatorType.AND));

        var otherConditions = toConditions();

        if (toConditions().isEmpty())
            conditions.get(conditions.size() - 1).setLogicalOperator(Condition.LogicalOperatorType.END);
        else
            conditions.addAll(otherConditions);

        return conditions;
    }

    @Override
    public boolean isEmpty() {

        return operationDateTime == null && operationKind == null && transactionSum == null && currencyFrom == null;
    }

    @Override
    public void fillEmptyFields() {

        if (operationDateTimeOT == null)
            operationDateTimeOT = DEFAULT_OPERATION_DATE_TIME_OPERATION_TYPE;

        if (operationKindOT == null)
            operationKindOT = DEFAULT_OPERATION_KIND_OPERATION_TYPE;

        if (transactionSumOT == null)
            transactionSumOT = DEFAULT_TRANSACTION_SUM_OPERATION_TYPE;

        if (currencyFromOT == null)
            currencyFromOT = DEFAULT_CURRENCY_FROM_OPERATION_TYPE;
    }
}
