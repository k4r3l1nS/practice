package com.practice.demo.dto.entity_dto;

import com.practice.demo.models.entities.Account;
import com.practice.demo.models.currency_info.Currency;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class AccountDto {

    private String accountName;

    private BigDecimal balance;

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

            accountEntity.setBalance(balance);
            accountEntity.setCurrency(currency);
        }

        if (accountKind != null && accountEntity.getAccountKind() != accountKind) {

            accountEntity.setAccountKind(accountKind);
            accountEntity.setLastCapitalization(accountKind.equals(Account.AccountKind.ACCUMULATIVE) ?
                    LocalDateTime.now() : null);
        }
    }

    public boolean hasEmptyFields() {

        return accountName == null || accountName.isEmpty() || balance == null ||
                currency == null || accountKind == null;
    }
}
