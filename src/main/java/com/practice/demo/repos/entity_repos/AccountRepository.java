package com.practice.demo.repos.entity_repos;

import com.practice.demo.models.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

     Account findAccountByName(String accountName);

     boolean existsByName(String name);

     @Query("select E from Account E where E.isActive = true and E.accountKind = 1")
     List<Account> findActiveAccumulationAccounts();
}
