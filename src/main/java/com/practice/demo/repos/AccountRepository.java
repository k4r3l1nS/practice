package com.practice.demo.repos;

import com.practice.demo.models.Account;
import com.practice.demo.models.db_views.AccountView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

     Account findAccountByName(String accountName);

     @Query("SELECT E FROM AccountView E")
     List<AccountView> findAllAccounts();

     @Query("select E from AccountView E where E.clientId=?1 and (E.isActive is null or E.isActive=true)")
     List<AccountView> findAllAccountsByClientId(Long clientId);

     @Query("select E from AccountView E where E.accountId=?1")
     AccountView findAccountById(Long accountId);
}
