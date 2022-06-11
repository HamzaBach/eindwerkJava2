package com.example.eindwerkJava2.repositories;

import com.example.eindwerkJava2.model.Vat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VatRepository extends JpaRepository<Vat, Long> {
    //TODO below method disregards active=0, should be extended.
    boolean existsVatByVatRate(double vatRate);
    Optional<Vat> findByVatRate(double vatRate);

    List<Vat> findByActive(int active);
}
