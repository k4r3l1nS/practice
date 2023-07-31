package com.practice.demo.dto.specification.models;

import com.practice.demo.dto.specification.SpecificationDto;
import com.practice.demo.models.specification.Condition;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class OperationSpecificationDto implements SpecificationDto {

    public final static String DEFAULT_OPERATION_DATE_TIME_OT = ">";
    public final static String DEFAULT_OPERATION_KIND_OT = "=";
    public final static String DEFAULT_SUM_OT = ">";


    private LocalDateTime operationDateTime;
    private OperationKind operationKind;
    private Double sum;

    private String operationDateTimeOT;
    private String operationKindOT;
    private String sumOT;


    @Getter
    private enum OperationKind {

        DEPOSIT("DEPOSIT"),
        WITHDRAWAL("WITHDRAWAL");

        private final static Map<String, OperationKind> _map;

        static {

            _map = Stream.of(values()).collect(Collectors.toMap(OperationKind::getName, Function.identity()));
        }

        private final String name;

        OperationKind(String name) {
            this.name = name;
        }

        public static OperationKind resolveByName(String name) {

            return _map.getOrDefault(name, null);
        }
    }

    @Override
    public List<Condition> toConditions() {

        List<Condition> conditions = new ArrayList<>();

        if (operationDateTime != null) {

            conditions.add(new Condition("operationDateTime", Arrays.asList(operationDateTime.toString()),
                    Condition.OperationType.resolveByName(operationDateTimeOT),
                    Condition.LogicalOperatorType.AND));
        }

        if (operationKind != null) {

            conditions.add(new Condition("operationKind", Arrays.asList(operationKind.toString()),
                    Condition.OperationType.resolveByName(operationKindOT),
                    Condition.LogicalOperatorType.AND));
        }

        if (sum != null) {

            conditions.add(new Condition("sum", Arrays.asList(sum.toString()),
                    Condition.OperationType.resolveByName(sumOT),
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

        return operationDateTime == null && operationKind == null && sum == null;
    }

    @Override
    public void fillEmptyFields() {

        if (operationDateTimeOT == null)
            operationDateTimeOT = DEFAULT_OPERATION_DATE_TIME_OT;

        if (operationKindOT == null)
            operationKindOT = DEFAULT_OPERATION_KIND_OT;

        if (sum == null)
            sumOT = DEFAULT_SUM_OT;
    }
}