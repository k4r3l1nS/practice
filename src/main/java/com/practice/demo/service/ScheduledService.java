package com.practice.demo.service;

import com.practice.demo.models.entities.Account;
import com.practice.demo.repos.entity_repos.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ScheduledService {

    private final AccountRepository accountRepository;

    /**
     * Time between handling capitalization operations
     */
    public static final long capitalizationIntervalInMs = 900000;

    /**
     * Capitalizes active accumulation accounts if necessary every {fixedRate} ms
     */
    @Scheduled(fixedRate = 5000)
    public void capitalize() {

        List<Account> accountList = accountRepository.findActiveAccumulationAccounts();

        for (var account : accountList) {

            if (account.capitalizationIsOverdue(capitalizationIntervalInMs)) {

                account.capitalize();
//                System.out.println("capitalized!");
            }
        }
    }
}
