package com.practice.demo.dto;

import com.practice.demo.models.Account;
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

    private Double sum;

    private String currency;

    public Account toEntity() {

        var account = new Account();

        account.setName(accountName);
        account.setCurrency(Currency.resolveByName(currency));

        return account;
    }

    @Transactional
    public void mapTo(Account accountEntity) {

        if (this.accountName != null && !this.accountName.isEmpty()) {

            accountEntity.setName(accountName);
        }

        if (this.currency != null && !this.currency.isEmpty()) {

            double[][] rates = new CurrencyRates().getRates();

            accountEntity.setBalance(accountEntity.getBalance()
                    * rates[accountEntity.getCurrency().ordinal()][Currency.resolveByName(this.currency).ordinal()]);

            accountEntity.setCurrency(Currency.resolveByName(currency));
        }
    }
}
