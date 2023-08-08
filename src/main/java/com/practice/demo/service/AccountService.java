package com.practice.demo.service;

import com.practice.demo.dto.entity_dto.AccountDto;
import com.practice.demo.dto.entity_dto.TransferBetweenAccountsDto;
import com.practice.demo.dto.specification_dto.models.AccountSpecificationDto;
import com.practice.demo.dto.paging_and_sotring_dto.PagingAndSortingDto;
import com.practice.demo.exceptions.models.*;
import com.practice.demo.models.currency_info.CurrencyRates;
import com.practice.demo.models.entities.Account;
import com.practice.demo.models.entities.Operation;
import com.practice.demo.models.db_views.AccountView;
import com.practice.demo.models.specification.Condition;
import com.practice.demo.models.specification.SpecificationBuilder;
import com.practice.demo.repos.entity_repos.AccountRepository;
import com.practice.demo.repos.entity_repos.ClientRepository;
import com.practice.demo.repos.db_view_repos.AccountViewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.security.auth.login.AccountNotFoundException;
import java.util.Arrays;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class AccountService {

    private final ClientRepository clientRepository;

    private final AccountRepository accountRepository;
    private final AccountViewRepository accountViewRepository;

    public void addAccount(AccountDto accountDto, Long clientId)
            throws AccountNameAlreadyTakenException,
            InvalidSumInputException, EmptyFieldException, ResourceNotFoundException {

        if (accountDto.hasEmptyFields()) {

            throw new EmptyFieldException("All fields and radio buttons must be filled in");
        }

        if (accountRepository.existsByName(accountDto.getAccountName())
                && accountRepository.findByName(accountDto.getAccountName()).isActive()) {

            throw new AccountNameAlreadyTakenException("This account name is already taken");
        }

        if (accountDto.getFirstDeposit() <= 0) {

            throw new InvalidSumInputException("First deposit is mandatory and must be above 0");
        }

        var client = clientRepository.findById(clientId)
                .orElseThrow(() -> new ResourceNotFoundException("Client with id = " + clientId + " not found"));
        var account = accountDto.toEntity();

        account.addOperation(Operation.getOperation(Operation.OperationKind.DEPOSIT,
                accountDto.getFirstDeposit(), account.getCurrency()));

        client.addAccount(account);

        accountRepository.save(account);
    }

    public void updateAccount(AccountDto accountDto, Long accountId) {

        if (accountRepository.existsByName(accountDto.getAccountName())
                && accountRepository.findByName(accountDto.getAccountName()).isActive()) {

            throw new AccountNameAlreadyTakenException("This account name is already taken");
        }

        var accountEntity = accountRepository.findById(accountId)
                .orElseThrow(() -> new ResourceNotFoundException("Account with id = " + accountId + " not found"));

        accountDto.mapTo(accountEntity);
    }

    public Account findById(Long accountId) {

        return accountRepository.findById(accountId)
                .orElseThrow(() -> new ResourceNotFoundException("Account with id = " + accountId + " not found"));
    }

    @Transactional(readOnly = true)
    public AccountView findAccountViewById(Long accountId) {

        return accountViewRepository.findAccountViewById(accountId);
    }

    public void deactivateAccountById(Long accountId) {

        var account = accountRepository.findById(accountId)
                .orElseThrow(() -> new ResourceNotFoundException("Account with id = " + accountId + " not found"));

        account.setActive(false);
        account.setBalance(0.0);

        //some type of withdrawal
    }

    public Page<AccountView> fetchNextPageByClientId(PagingAndSortingDto pagingAndSortingDto,
                                                     AccountSpecificationDto accountSpecificationDto, Long clientId) {

        var conditions = accountSpecificationDto.toConditions(clientId);

        var specification = new SpecificationBuilder<>().with(conditions).build();
        var pageRequest = pagingAndSortingDto.toPageRequest();

        return accountViewRepository.findAll(specification, pageRequest);
    }

    public AccountView findOneAccountView(Long clientId) {

        var specification = new SpecificationBuilder<>()
                .with(Condition.builder()
                        .fieldName("clientId").operation(Condition.OperationType.EQUALS)
                        .value(clientId).logicalOperator(Condition.LogicalOperatorType.AND)
                        .build())
                .with(Condition.builder()
                        .fieldName("isActive").operation(Condition.OperationType.IN)
                        .values(Arrays.asList(true, null)).logicalOperator(Condition.LogicalOperatorType.END)
                        .build())
                .build();

        List<AccountView> accountView =  accountViewRepository.findAll(specification);

        return accountView.get(0);
    }

    public void transferBetweenAccounts(TransferBetweenAccountsDto transferBetweenAccountsDto, Long clientId) {

        if (transferBetweenAccountsDto.hasEmptyFields()) {

            throw new EmptyFieldException("All fields and radio buttons must be filled in");
        }

        Account accountFrom = accountRepository
                .findByNameAndClientId(transferBetweenAccountsDto.getAccountFromName(), clientId);
        Account accountTo = accountRepository.findByName(transferBetweenAccountsDto.getAccountToName());

        if (accountFrom == null) {

            RuntimeException exception = accountRepository.existsByName(transferBetweenAccountsDto.getAccountFromName()) ?
                    new ForbiddenResourceException("This account does not belong to client (id = " + clientId + ")") :
                    new ResourceNotFoundException("Could not find account to withdraw money from");

            throw exception;
        }

        if (accountTo == null) {

            throw new ResourceNotFoundException("Could not find account to transfer money to");
        }

        if (!accountFrom.isEnoughMoney(transferBetweenAccountsDto.getTransactionSum(),
                transferBetweenAccountsDto.getCurrency())) {

            throw new NotEnoughMoneyException("Not enough money on account");
        }

        if (!accountFrom.getId().equals(accountTo.getId())) {

            var withdrawalOperation = Operation.getOperation(Operation.OperationKind.WITHDRAWAL,
                    transferBetweenAccountsDto.getTransactionSum(), transferBetweenAccountsDto.getCurrency());
            var depositOperation = Operation.getOperation(Operation.OperationKind.DEPOSIT,
                    transferBetweenAccountsDto.getTransactionSum(), transferBetweenAccountsDto.getCurrency());

            accountFrom.addOperation(withdrawalOperation);
            accountTo.addOperation(depositOperation);
        }
    }
}
