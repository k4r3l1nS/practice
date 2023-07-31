package com.practice.demo.dto;

import com.practice.demo.models.db_views.OperationView;
import com.practice.demo.models.rates.Currency;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OperationListDto {

    private Long clientId;

    private Long accountId;

    private String ownerFullName;

    private String accountName;

    private Currency currency;

    private double balance;

    List<OperationInfo> operations = new ArrayList<>();

    public void addOperation(OperationView operationView) {

        operations.add(new OperationInfo(operationView.getOperationId(), operationView.getDeposit(),
                operationView.getWithdrawal(), operationView.getOperationDateTime(), operationView.getAccountName()));
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public class OperationInfo {

        private Long operationId;

        private Double deposit;

        private Double withdrawal;

        private LocalDateTime operationDateTime;

        private String accountName;
    }

    public static OperationListDto valueFrom(List<OperationView> operationViews) {

        OperationListDto operationListDto = new OperationListDto();

        operationListDto.setBalance(operationViews.get(0).getBalance());
        operationListDto.setAccountName(operationViews.get(0).getAccountName());
        operationListDto.setOwnerFullName(operationViews.get(0).getOwnerFullName());
        operationListDto.setCurrency(operationViews.get(0).getCurrency());
        operationListDto.setAccountId(operationViews.get(0).getAccountId());
        operationListDto.setClientId(operationViews.get(0).getClientId());

        operationViews.stream().forEach(operationView -> operationListDto.addOperation(operationView));

        return operationListDto;
    }
}
