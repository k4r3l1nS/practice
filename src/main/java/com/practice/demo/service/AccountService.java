package com.practice.demo.service;

import com.practice.demo.dto.AccountDto;
import com.practice.demo.dto.AccountListDto;
import com.practice.demo.dto.specification.models.AccountSpecificationDto;
import com.practice.demo.dto.OperationListDto;
import com.practice.demo.dto.paging_and_sotring.PagingAndSortingDto;
import com.practice.demo.exceptions.model.AccountNameAlreadyTakenException;
import com.practice.demo.exceptions.model.InvalidSumInputException;
import com.practice.demo.models.Account;
import com.practice.demo.models.db_views.AccountView;
import com.practice.demo.models.specification.Condition;
import com.practice.demo.models.specification.SpecificationBuilder;
import com.practice.demo.repos.AccountRepository;
import com.practice.demo.repos.ClientRepository;
import com.practice.demo.repos.OperationRepository;
import com.practice.demo.repos.db_view_repos.AccountViewRepositoty;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;


@Service
@Transactional
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final ClientRepository clientRepository;
    private final OperationRepository operationRepository;

    private final AccountViewRepositoty accountViewRepositoty;

    public void addAccount(AccountDto accountDto, Long clientId) throws InvalidSumInputException {

        if (accountRepository.findAccountByName(accountDto.getAccountName()) != null
                && accountRepository.findAccountByName(accountDto.getAccountName()).isActive()) {

            throw new AccountNameAlreadyTakenException("This account name is already taken");
        }

        if (accountDto.getSum() == null || accountDto.getSum() <= 0) {

            throw new InvalidSumInputException("First deposit is mandatory and must be above 0");
        }

        var client = clientRepository.findById(clientId).orElseThrow();

        var account = accountDto.toEntity();

        account.deposit(accountDto.getSum());

        client.addAccount(account);

        accountRepository.save(account);
    }

    public void updateAccount(AccountDto accountDto, Long accountId) {

        if (accountRepository.findAccountByName(accountDto.getAccountName()) != null
                && accountRepository.findAccountByName(accountDto.getAccountName()).isActive()) {

            throw new AccountNameAlreadyTakenException("This account name is already taken");
        }

        var accountEntity = accountRepository.findById(accountId).orElseThrow();

        accountDto.mapTo(accountEntity);
    }

    public Account findById(Long accountId) {

        return accountRepository.findById(accountId).orElseThrow();
    }

    @Transactional(readOnly = true)
    public AccountView findAccountById(Long accountId) {

        var view = accountRepository.findAccountById(accountId);

        return view;
        //return AccountListDto.valueFrom(view);
    }

    @Transactional(readOnly = true)
    public OperationListDto findAllOperationsByAccountId(Long accountId) {

        var operationViewList = operationRepository.findAllOperationsByAccountId(accountId);

        return OperationListDto.valueFrom(operationViewList);
    }

    public void deactivateAccountById(Long accountId) {

        var account = accountRepository.findById(accountId).orElseThrow();

        account.setActive(false);
        account.setBalance(0.0);

        //some type of withdrawal
    }

    public Page<AccountView> fetchNextPageByClientId(PagingAndSortingDto pagingAndSortingDto,
                                                     AccountSpecificationDto accountSpecificationDto, Long clientId) {

        var conditions = accountSpecificationDto.toConditions(clientId);

        var specification = new SpecificationBuilder<>().with(conditions).build();
        var pageRequest = pagingAndSortingDto.toPageRequest();

        return accountViewRepositoty.findAll(specification, pageRequest);
    }

    public String getFullNameFromViewByClientId(Long clientId) {

        var specification = new SpecificationBuilder<>()
                .with(new Condition("clientId", Arrays.asList(clientId.toString()), Condition.OperationType.EQUALS,
                        Condition.LogicalOperatorType.AND))
                .with(new Condition("isActive", Arrays.asList(true, null), Condition.OperationType.IN,
                        Condition.LogicalOperatorType.END))
                .build();

        List<AccountView> accountView =  accountViewRepositoty.findAll(specification);

        return accountView.get(0).getFullName();
    }
}
