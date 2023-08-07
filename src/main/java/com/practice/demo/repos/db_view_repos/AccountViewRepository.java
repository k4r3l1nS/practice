package com.practice.demo.repos.db_view_repos;

import com.practice.demo.models.db_views.AccountView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountViewRepository extends JpaRepository<AccountView, Long>, JpaSpecificationExecutor<AccountView> {

    @Query("select E from AccountView E where E.accountId = :accountId")
    AccountView findAccountViewById(Long accountId);
}
