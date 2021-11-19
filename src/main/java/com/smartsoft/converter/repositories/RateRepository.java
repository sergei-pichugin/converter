package com.smartsoft.converter.repositories;

import com.smartsoft.converter.entities.Rate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository("rateRepository")
public interface RateRepository extends JpaRepository<Rate, Long> {
    List<Rate> findByCharCodeAndDate(String code, LocalDate date);

    @Query(value = "SELECT max(date) FROM Rate")
    LocalDate findLastDate();
}
