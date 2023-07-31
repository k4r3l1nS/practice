package com.practice.demo.service;

import com.practice.demo.dto.OperationDto;
import com.practice.demo.dto.OperationListDto;
import com.practice.demo.dto.paging_and_sotring.PagingAndSortingDto;
import com.practice.demo.dto.specification.models.AccountSpecificationDto;
import com.practice.demo.dto.specification.models.OperationSpecificationDto;
import com.practice.demo.exceptions.model.EmptyRadioValueException;
import com.practice.demo.exceptions.model.InvalidSumInputException;
import com.practice.demo.models.Operation;
import com.practice.demo.models.db_views.AccountView;
import com.practice.demo.models.db_views.OperationView;
import com.practice.demo.models.specification.Condition;
import com.practice.demo.models.specification.SpecificationBuilder;
import com.practice.demo.repos.AccountRepository;
import com.practice.demo.repos.ClientRepository;
import com.practice.demo.repos.OperationRepository;
import com.practice.demo.repos.db_view_repos.OperationViewRepository;
import lombok.RequiredArgsConstructor;
import org.antlr.v4.runtime.tree.pattern.ParseTreePattern;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class OperationService {

    private final AccountRepository accountRepository;
    private final OperationRepository operationRepository;
    private final OperationViewRepository operationViewRepository;

    public void addOperation(OperationDto operationDto, Long accountId)
            throws InvalidSumInputException, EmptyRadioValueException {

        if (operationDto.getOperationType() == null || operationDto.getCurrencyFrom() == null) {

            throw new EmptyRadioValueException("All fields and radio buttons must be filled in");
        }

        if (operationDto.getSum() == null || operationDto.getSum() <= 0) {

            throw new InvalidSumInputException("Sum must be entered and be above 0");
        }

        var account = accountRepository.findById(accountId).orElseThrow();

        var currencyTo = account.getCurrency();

        var operation = operationDto.toEntity(currencyTo);
        operationRepository.save(operation);

        account.addOperation(operation);
    }

    public Operation findById(Long operationId) {

        return operationRepository.findById(operationId).orElseThrow();
    }

    public OperationView findOperationById(Long operationId) {

        return operationRepository.findOperationById(operationId);
    }

    public OperationView findOneOperationView(Long accountId) {

        var specification = new SpecificationBuilder<>()
                .with(new Condition("accountId", Arrays.asList(accountId.toString()), Condition.OperationType.EQUALS,
                        Condition.LogicalOperatorType.AND))
                .build();

        List<OperationView> operationView =  operationViewRepository.findAll(specification);

        return operationView.get(0);
    }

    public Page<OperationView> fetchNextPageByAccountId(PagingAndSortingDto pagingAndSortingDto,
                                                        OperationSpecificationDto operationSpecificationDto, Long accountId) {

        var conditions = operationSpecificationDto.toConditions(accountId);

        var specification = new SpecificationBuilder<>().with(conditions).build();
        var pageRequest = pagingAndSortingDto.toPageRequest();

        return operationViewRepository.findAll(specification, pageRequest);
    }
}
