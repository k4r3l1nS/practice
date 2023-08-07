package com.practice.demo.dto.entity_dto;

import com.practice.demo.models.entities.Account;
import com.practice.demo.models.currency_info.Currency;
import com.practice.demo.models.currency_info.CurrencyRates;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class AccountDto {

    private String accountName;

    private Double firstDeposit;

    private Currency currency;

    private Account.AccountKind accountKind;

    public Account toEntity() {

        var account = new Account();

        account.setName(accountName);
        account.setCurrency(currency);
        account.setAccountKind(accountKind);

        account.setLastCapitalization(accountKind.equals(Account.AccountKind.ACCUMULATIVE) ? LocalDateTime.now() : null);

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

        if (accountKind != null && accountEntity.getAccountKind() != accountKind) {

            accountEntity.setAccountKind(accountKind);
            accountEntity.setLastCapitalization(accountKind.equals(Account.AccountKind.ACCUMULATIVE) ?
                    LocalDateTime.now() : null);
        }
    }

    public boolean hasEmptyFields() {

        return accountName == null || accountName.isEmpty() || firstDeposit == null ||
                currency == null || accountKind == null;
    }
}
