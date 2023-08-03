package com.practice.demo.models.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "client")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "first_name", length = 50)
    private String firstName;

    @Column(name = "last_name", length = 50)
    private String lastName;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Column(name = "registration_date")
    private LocalDateTime registration_date;

    @Column(name = "email")
    private String email;

    @Column(name = "is_active")
    private boolean isActive = true;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "client")
    private List<Account> accounts = new ArrayList<>();

    public void addAccount(Account account) {

        Objects.requireNonNull(account);
        this.accounts.add(account);
        account.setClient(this);
    }

    public Client() {

        registration_date = LocalDateTime.now();
    }
}
