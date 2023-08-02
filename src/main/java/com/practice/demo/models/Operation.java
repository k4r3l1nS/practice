package com.practice.demo.models;


import com.practice.demo.models.rates.Currency;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
@Setter
@Entity
@Table(name = "operations")
public class Operation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;

    @Column(name = "operation_kind")
    @Enumerated
    private OperationKind operationKind;

    @Column(name = "transaction_sum")
    private Double transactionSum;

    @Column(name = "operation_date")
    private LocalDateTime operationDateTime;

    @Enumerated
    @Column(name = "currency_from")
    private Currency currencyFrom;

    public Operation() {

        this.operationDateTime = LocalDateTime.now();
    }

    public static Operation getOperation(OperationKind operationKind, Double transactionSum, Currency currencyFrom) {

        Operation operation = new Operation();

        operation.setOperationKind(operationKind);
        operation.setTransactionSum(transactionSum);
        operation.setCurrencyFrom(currencyFrom);

        return operation;
    }

    @Getter
    public enum OperationKind {

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
}
