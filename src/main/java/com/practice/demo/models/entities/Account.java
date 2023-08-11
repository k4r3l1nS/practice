package com.practice.demo.models.entities;

import com.practice.demo.exceptions.models.NotEnoughMoneyException;
import com.practice.demo.models.currency_enum.Currency;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Entity
@Getter
@Setter
@Table(name = "account")
@NoArgsConstructor
public class Account {

    /**
    * Unique account id
    */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * Account name
     */
    @Column(name = "account_name", length = 30)
    private String name;

    /**
     * Account currency type
     */
    @Enumerated
    @Column(name = "currency")
    private Currency currency;

    /**
     * Account balance
     */
    @Column(name = "balance")
    private BigDecimal balance = BigDecimal.ZERO;

    /**
     * Account kind: accumulative or common
     */
    @Enumerated
    @Column(name = "account_kind")
    private AccountKind accountKind;

    /**
     * List of operations related to account
     */
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "account")
    private List<Operation> operations = new ArrayList<>();

    /**
     * Account owner entity
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id")
    private Client client;

    /**
     * Whether account active or not
     */
    @Column(name = "is_active")
    private boolean isActive = true;

    /**
     * Date & time of last capitalization
     */
    @Column(name = "last_capitalization")
    private LocalDateTime lastCapitalization;

    /**
     * Nested class representing operation kind
     */
    @Getter
    public enum AccountKind {

        COMMON("COMMON"),
        ACCUMULATIVE("ACCUMULATIVE");

        public final double ACCUMULATION_COEFFICIENT_PER_YEAR = 1.12;

        private final static Map<String, AccountKind> _map;

        static {

            _map = Stream.of(values()).collect(Collectors.toMap(AccountKind::getName, Function.identity()));
        }

        private final String name;

        AccountKind(String name) {
            this.name = name;
        }

        public static AccountKind resolveByName(String name) {

            return _map.getOrDefault(name, null);
        }
    }

    /**
     * Adds capitalization operation to account
     */
    public void capitalize() {

        var finalSum = getPercentageCoefficient().multiply(balance);

        var operation = Operation.getOperation(Operation.OperationKind.DEPOSIT,
                finalSum, currency);

        addOperation(operation, finalSum);
        lastCapitalization = LocalDateTime.now();
    }

    /**
     * Links operation entity to account
     *
     * @param operation operation entity
     * @throws NotEnoughMoneyException not enough money on balance
     */
    public void addOperation(Operation operation, BigDecimal finalSum) throws NotEnoughMoneyException {

        Objects.requireNonNull(operation);

        switch (operation.getOperationKind()) {

            case DEPOSIT -> balance = balance.add(finalSum);

            case WITHDRAWAL -> {

                if (!isEnoughMoney(finalSum)) {

                    throw new NotEnoughMoneyException("There is not enough money on balance");
                }

                balance = balance.subtract(finalSum);
            }
        }

        this.operations.add(operation);
        operation.setAccount(this);
    }

    /**
     * Operates coefficient from last capitalization
     *
     * @return coefficient
     */
    public BigDecimal getPercentageCoefficient() {

        if (!accountKind.equals(AccountKind.ACCUMULATIVE))
            return null;

        long intervalInSeconds = Math.round((double)Duration.between(getLastCapitalization(), LocalDateTime.now())
                .toMillis() / 1000);
        long secondsInYear = 31557600L;

        return BigDecimal.valueOf(Math.pow(accountKind.ACCUMULATION_COEFFICIENT_PER_YEAR,
                (double)intervalInSeconds / secondsInYear) - 1);
    }

    /**
     * Checks if there is enough money on balance
     *
     * @param sum withdrawal sum
     * @return whether if enough money or not
     */
    public boolean isEnoughMoney(BigDecimal sum) {

        return balance.compareTo(sum) >= 0;
    }
}
