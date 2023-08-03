package com.practice.demo.models.entities;

import com.practice.demo.exceptions.models.NotEnoughMoneyException;
import com.practice.demo.models.currency_info.Currency;
import com.practice.demo.models.currency_info.CurrencyRates;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Entity
@Getter
@Setter
@Table(name = "account")
@NoArgsConstructor
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "account_name", length = 30)
    private String name;

    @Enumerated
    @Column(name = "currency")
    private Currency currency;

    @Column(name = "balance")
    private Double balance = 0.0;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "account")
    private List<Operation> actions = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id")
    private Client client;

    @Column(name = "is_active")
    private boolean isActive = true;

    public void addOperation(Operation operation) throws NotEnoughMoneyException {

        Objects.requireNonNull(operation);

        switch (operation.getOperationKind()) {

            case DEPOSIT -> balance += new CurrencyRates().convert(
                    operation.getCurrencyFrom(), currency, operation.getTransactionSum());

            case WITHDRAWAL -> {

                double withdrawalSum = new CurrencyRates().convert(
                        operation.getCurrencyFrom(), currency, operation.getTransactionSum());

                if (balance < withdrawalSum) {

                    throw new NotEnoughMoneyException("There is not enough money on balance");
                }

                balance -= new CurrencyRates().convert(
                        operation.getCurrencyFrom(), currency, operation.getTransactionSum());
            }
        }

        this.actions.add(operation);
        operation.setAccount(this);
    }
}
