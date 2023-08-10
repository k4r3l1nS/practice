package com.practice.demo.models.db_views;


import com.practice.demo.models.currency_enum.Currency;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.Getter;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Subselect;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Immutable
@Getter
@Subselect("select * from currency_rates_view")
public class CurrencyRatesView {

    @Id
    @Column(name = "char_code")
    private String charCode;

    @Column(name = "last_update")
    private LocalDateTime lastUpdate;

    @Column(name = "value")
    private BigDecimal value;

    @Enumerated
    @Column(name = "currency")
    private Currency currency;
}
