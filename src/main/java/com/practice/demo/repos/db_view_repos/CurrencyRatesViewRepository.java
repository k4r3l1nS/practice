package com.practice.demo.repos.db_view_repos;

import com.practice.demo.models.db_views.CurrencyRatesView;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CurrencyRatesViewRepository extends JpaRepository<CurrencyRatesView, String> {
}
