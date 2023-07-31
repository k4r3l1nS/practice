package com.practice.demo.models;

import com.practice.demo.exceptions.model.NotEnoughMoneyException;
import com.practice.demo.models.rates.Currency;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Immutable;

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

    public void deposit(Double sum) {

        addOperation(Operation.getDeposit(sum));
    }

    public void withdraw(Double sum) {

        addOperation(Operation.getWithdrawal(sum));
    }

    public void addOperation(Operation operation) {

        if (operation.isDeposit()) {

            balance += operation.getDeposit();
        }
        else {

            if (balance < operation.getWithdrawal()) {

                throw new NotEnoughMoneyException("There is not enough money on balance");
            }

            balance -= operation.getWithdrawal();
        }

        Objects.requireNonNull(operation);
        this.actions.add(operation);
        operation.setAccount(this);
    }
}
