package com.smartsoft.converter.repositories;

import com.smartsoft.converter.entities.Conversion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ConversionRepository extends JpaRepository<Conversion, Long> {

    List<Conversion> findBySourceCurrencyAndTargetCurrency(String sourceCode, String targetCode);

    List<Conversion> findBySourceCurrencyAndTargetCurrencyAndConvertedAtBetween(
            String sourceCode, String targetCode, LocalDateTime start, LocalDateTime end);
}
