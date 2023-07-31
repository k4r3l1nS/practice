package com.practice.demo.models.db_views;

import com.practice.demo.models.rates.Currency;
import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Subselect;

import java.time.LocalDateTime;

@Entity
@Immutable
@Getter
@Subselect("select * from operation_view")
public class OperationView {

    @Id
    @Column(name = "operation_id")
    private Long operationId;

    @Id
    @Column(name = "account_id")
    private Long accountId;

    @Id
    @Column(name = "client_id")
    private Long clientId;

    @Column(name = "currency")
    private Currency currency;

    @Column(name = "owner_full_name")
    private String ownerFullName;

    @Column(name = "balance")
    private double balance;

    @Column(name = "deposit")
    private Double deposit;

    @Column(name = "withdrawal")
    private Double withdrawal;

    @Column(name = "operation_date")
    private LocalDateTime operationDateTime;

    @Column(name = "account_name")
    private String accountName;
}
