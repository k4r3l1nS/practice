package com.practice.demo.repos;

import com.practice.demo.repos.entity_repos.AccountRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AccountRepositoryTest {

    @Autowired
    private AccountRepository accountRepository;

    @Test
    void findAllAccountsByClientId() {

//        var accountsByClient = accountRepository.findAllAccountsByClientId(852L);
//        System.out.println(accountsByClient);
    }
}