package com.practice.demo.dto.entity_dto;

import com.practice.demo.models.currency_info.Currency;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
public class TransferBetweenAccountsDto {

    private String accountFromName;
    private String accountToName;
    private BigDecimal transactionSum;
    private Currency currency;

    public boolean hasEmptyFields() {

        return accountFromName == null ||accountFromName.isEmpty() || accountToName == null
                || accountToName.isEmpty() || transactionSum == null || currency == null;
    }
}
