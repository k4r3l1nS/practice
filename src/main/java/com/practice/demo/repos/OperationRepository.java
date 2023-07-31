package com.practice.demo.repos;

import com.practice.demo.models.Operation;
import com.practice.demo.models.db_views.OperationView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OperationRepository extends JpaRepository<Operation, Long>, PagingAndSortingRepository<Operation, Long> {

    @Query("select E from OperationView E where E.accountId=?1")
    List<OperationView> findAllOperationsByAccountId(Long accountId);

    @Query("select E from OperationView E where E.operationId=?1")
    OperationView findOperationById(Long operationId);
}
