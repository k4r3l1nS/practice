package com.practice.demo.dto;

import com.practice.demo.models.db_views.AccountView;
import com.practice.demo.models.rates.Currency;
import lombok.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class AccountListDto {

    private String fullName;

    private Page<AccountInfo> accounts;

    private Long clientId;

    @Getter
    @Setter
    @Builder
    private final static class AccountInfo {

        private Long accountId;

        private String accountName;

        private Double balance;

        private Currency currency;

        private Integer numberOfOperations;

        private LocalDateTime latestOperation;
    }

    public boolean isEmpty() {

        return accounts.isEmpty() || accounts.get().findFirst().get().accountId == null;
    }

    public static AccountListDto valueFrom(Page<AccountView> accountViews, Long clientId, String fullName) {

        AccountListDto accountListDto = new AccountListDto();

        accountListDto.setClientId(clientId);
        accountListDto.setFullName(fullName);

        Page<AccountInfo> accountInfoPage = accountViews.isEmpty() ? Page.empty() :
                accountViews.map(accountView ->
                AccountInfo.builder()
                        .accountId(accountView.getAccountId()).accountName(accountView.getAccountName())
                        .balance(accountView.getBalance()).currency(accountView.getCurrency())
                        .numberOfOperations(accountView.getNumberOfOperations())
                        .latestOperation(accountView.getLatestOperation())
                        .build());

        accountListDto.setAccounts(accountInfoPage);

        return accountListDto;
    }
}
