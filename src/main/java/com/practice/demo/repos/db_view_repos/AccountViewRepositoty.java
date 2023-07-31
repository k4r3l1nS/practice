package com.practice.demo.repos.db_view_repos;

import com.practice.demo.models.db_views.AccountView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountViewRepositoty extends JpaRepository<AccountView, Long>, JpaSpecificationExecutor<AccountView> {
}
