package com.smartsoft.converter.repositories;

import com.smartsoft.converter.entities.Conversion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ConversionRepository extends JpaRepository<Conversion, Long> {

    List<Conversion> findBySourceCodeAndTargetCode(String sourceCode, String targetCode);

    List<Conversion> findBySourceCodeAndTargetCodeAndConvertedAtBetween(
            String sourceCode, String targetCode, LocalDateTime start, LocalDateTime end);
}
