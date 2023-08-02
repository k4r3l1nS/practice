package com.practice.demo.dto;

import com.practice.demo.models.Account;
import com.practice.demo.models.Operation;
import com.practice.demo.models.db_views.AccountView;
import com.practice.demo.models.rates.Currency;
import com.practice.demo.models.rates.CurrencyRates;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.transaction.annotation.Transactional;

@Getter
@Setter
@Builder
public class AccountDto {

    private String accountName;

    private Double firstDeposit;

    private Currency currency;

    public Account toEntity() {

        var account = new Account();

        account.setName(accountName);
        account.setCurrency(currency);

        return account;
    }

    @Transactional
    public void mapTo(Account accountEntity) {

        if (accountName != null && !accountName.isEmpty()) {

            accountEntity.setName(accountName);
        }

        if (currency != null) {

            double[][] rates = new CurrencyRates().getRates();

            accountEntity.setBalance(accountEntity.getBalance()
                    * rates[accountEntity.getCurrency().ordinal()][currency.ordinal()]);

            accountEntity.setCurrency(currency);
        }
    }
}
