package com.practice.demo.models;


import com.practice.demo.models.rates.Currency;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.function.Function;

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

    @Column(name = "deposit")
    private Double deposit;

    @Column(name = "withdrawal")
    private Double withdrawal;

    @Column(name = "operation_date")
    private LocalDateTime operationDateTime;

    @Enumerated
    @Column(name = "currency_from")
    private Currency currencyFrom;


    public Operation() {

        this.operationDateTime = LocalDateTime.now();
    }

    public Operation(boolean isDeposit, Double sum) {
        this();

        if (isDeposit) {

            this.deposit = sum;
        }
        else {

            this.withdrawal = sum;
        }
    }
    public static Operation getDeposit(Double sum) {

        return OperationKind.DEPOSIT.apply(sum);
    }

    public static Operation getWithdrawal(Double sum) {
        return OperationKind.WITHDRAW.apply(sum);
    }

    public boolean isWithdrawal() {
        return withdrawal != null;
    }

    public boolean isDeposit() {
        return deposit != null;
    }

    public enum OperationKind implements Function<Double, Operation> {
        DEPOSIT((sum) -> new Operation(true, sum)),
        WITHDRAW((sum) -> new Operation(false, sum));

        OperationKind(Function<Double, Operation> resolveOperation) {
            this.resolveOperation = resolveOperation;
        }

        private final Function<Double, Operation> resolveOperation;

        @Override
        public Operation apply(Double sum) {
            return resolveOperation.apply(sum);
        }
    }
}
