package com.practice.demo.repos;

import com.practice.demo.models.Client;
import com.practice.demo.models.db_views.ClientView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

    Client findClientByFirstNameAndLastNameAndBirthDate(String firstName, String lastName, LocalDate birthDate);

//    @Query("select client from Client client where client.accounts.size >= ?1")
//    Page<Client> fetchByNOrMoreAccounts(int n, Pageable pageable);

//    @Query(value = "select * from client where firstName = ?1", nativeQuery = true)
//    Page<Client> fetchByFirstName(String firstName, Pageable pageable);

//    @Query(value = "select client from Client client where client.birthDate.isAfter(?1)")
//    Page<Client> fetchByBirthDate(LocalDate date);

    @Query("select E from ClientView E")
    List<ClientView> getAllClients();

    @Query("select E from ClientView E where E.id=?1")
    ClientView findClientById(Long id);
}
