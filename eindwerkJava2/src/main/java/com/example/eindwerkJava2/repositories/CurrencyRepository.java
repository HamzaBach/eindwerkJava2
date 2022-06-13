package com.example.eindwerkJava2.repositories;

import com.example.eindwerkJava2.model.Currency;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CurrencyRepository extends JpaRepository<Currency,Long> {
    Currency findByCurrency(String currencyName);
}
