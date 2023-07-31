package com.practice.demo.dto.specification.models;

import com.practice.demo.dto.specification.SpecificationDto;
import com.practice.demo.models.Operation;
import com.practice.demo.models.rates.Currency;
import com.practice.demo.models.specification.Condition;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
@Setter
@Builder
public class AccountSpecificationDto implements SpecificationDto {

    public final static String DEFAULT_ACCOUNT_NAME_OPERATION_TYPE = "=";
    public final static String DEFAULT_BALANCE_OPERATION_TYPE = ">";
    public final static String DEFAULT_CURRENCY_OPERATION_TYPE = "=";
    public final static String DEFAULT_NUMBER_OF_OPERATIONS_OPERATION_TYPE = "=";
    public final static String DEFAULT_LATEST_OPERATION_OPERATION_TYPE = ">";

    private String accountName;
    private Double balance;
    private Currency currency;
    private Integer numberOfOperations;
    private LocalDateTime latestOperation;

    private String accountNameOT;
    private String balanceOT;
    private String currencyOT;
    private String numberOfOperationsOT;
    private String latestOperationOT;

    @Override
    public boolean isEmpty() {

        return (accountName == null || accountName.isEmpty()) && balance == null &&
                currency == null && numberOfOperations == null && latestOperation == null;
    }

    @Override
    public List<Condition> toConditions() {

        List<Condition> conditions = new ArrayList<>();

        conditions.add(new Condition("isActive", Arrays.asList(true, null),
                Condition.OperationType.IN, Condition.LogicalOperatorType.AND));

        if (accountName != null && !accountName.isEmpty()) {

            conditions.add(new Condition("accountName", Arrays.asList(accountName),
                    Condition.OperationType.resolveByName(accountNameOT),
                    Condition.LogicalOperatorType.AND));
        }

        if (balance != null) {

            conditions.add(new Condition("balance", Arrays.asList(balance.toString()),
                    Condition.OperationType.resolveByName(balanceOT),
                    Condition.LogicalOperatorType.AND));
        }

        if (currency != null) {

            conditions.add(new Condition("currency", Arrays.asList(currency.ordinal()),
                    Condition.OperationType.resolveByName(currencyOT),
                    Condition.LogicalOperatorType.AND));
        }

        if (latestOperation != null) {

            conditions.add(new Condition("latestOperation", Arrays.asList(latestOperation.toString()),
                    Condition.OperationType.resolveByName(latestOperationOT),
                    Condition.LogicalOperatorType.AND));
        }

        if (numberOfOperations != null) {

            conditions.add(new Condition("numberOfOperations", Arrays.asList(numberOfOperations.toString()),
                    Condition.OperationType.resolveByName(numberOfOperationsOT),
                    Condition.LogicalOperatorType.AND));
        }

        conditions.get(conditions.size() - 1).setLogicalOperator(Condition.LogicalOperatorType.END);

        return conditions;
    }

    public List<Condition> toConditions(Long clientId) {

        List<Condition> conditions = new ArrayList<>();

        conditions.add(new Condition("clientId", Arrays.asList(clientId.toString()), Condition.OperationType.EQUALS,
                Condition.LogicalOperatorType.AND));

        var otherConditions = toConditions();

        conditions.addAll(otherConditions);

        return conditions;
    }

    @Override
    public void fillEmptyFields() {

        if (currencyOT == null)
            currencyOT = DEFAULT_CURRENCY_OPERATION_TYPE;

        if (accountNameOT == null)
            accountNameOT = DEFAULT_ACCOUNT_NAME_OPERATION_TYPE;

        if (numberOfOperationsOT == null)
            numberOfOperationsOT = DEFAULT_NUMBER_OF_OPERATIONS_OPERATION_TYPE;

        if (latestOperationOT == null)
            latestOperationOT = DEFAULT_LATEST_OPERATION_OPERATION_TYPE;

        if (balanceOT == null)
            balanceOT = DEFAULT_BALANCE_OPERATION_TYPE;
    }
}
