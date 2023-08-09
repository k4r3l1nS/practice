package com.practice.demo.repos.entity_repos;

import com.practice.demo.models.entities.CurrencyRates;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CurrencyRatesRepository extends JpaRepository<CurrencyRates, String> {

    CurrencyRates findByCharCode(String charCode);
}
