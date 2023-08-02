package com.practice.demo.dto;

import com.practice.demo.models.Operation;
import com.practice.demo.models.db_views.OperationView;
import com.practice.demo.models.rates.Currency;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import lombok.*;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OperationListDto {

    CommonInfo commonInfo;
    Page<OperationInfo> operations;

    @Getter
    @Setter
    @Builder
    private final static class CommonInfo {

        private Long clientId;
        private Long accountId;
        private String ownerFullName;
        private String accountName;
        private Currency currency;
        private Double balance;
    }

    @Getter
    @Setter
    @Builder
    public final static class OperationInfo {

        private Long operationId;

        private Operation.OperationKind operationKind;

        private Double transactionSum;

        private Currency currencyFrom;

        private LocalDateTime operationDateTime;
    }

    public boolean isEmpty() {

        return operations.isEmpty();
    }

    public static OperationListDto valueFrom(Page<OperationView> operationViews, OperationView representativeView) {

        OperationListDto operationListDto = new OperationListDto();

        operationListDto.setCommonInfo(CommonInfo.builder()
                        .accountId(representativeView.getAccountId()).accountName(representativeView.getAccountName())
                        .balance(representativeView.getBalance()).clientId(representativeView.getClientId())
                        .currency(representativeView.getAccountCurrency()).ownerFullName(representativeView.getOwnerFullName())
                .build());

        Page<OperationInfo> operationInfoPage = operationViews.isEmpty() ? Page.empty() :
                operationViews.map(operationView ->
                        OperationInfo.builder()
                                .operationId(operationView.getOperationId()).operationKind(operationView.getOperationKind())
                                .transactionSum(operationView.getTransactionSum()).currencyFrom(operationView.getCurrencyFrom())
                                .operationDateTime(operationView.getOperationDateTime())
                                .build());

        operationListDto.setOperations(operationInfoPage);

        return operationListDto;
    }
}
