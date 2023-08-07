package com.practice.demo.service;

import com.practice.demo.models.entities.Account;
import com.practice.demo.repos.entity_repos.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ScheduledService {

    public static final long capitalizationIntervalInMs = 900000;

    private final AccountRepository accountRepository;

    /**
     * Capitalizes active accumulation accounts every {fixedRate} ms
     */
    @Scheduled(fixedRate = capitalizationIntervalInMs)
    public void addPercents() {

        List<Account> accountList = accountRepository.findActiveAccumulationAccounts();


        System.out.println("  [log] |" + LocalDateTime.now() + "| : CAPITALIZED! " +
                (accountList.isEmpty()?"(empty)": "(" + accountList.size() +" account(-s) served)"));


        for (var account : accountList) {

            account.capitalize();
        }
    }
}
