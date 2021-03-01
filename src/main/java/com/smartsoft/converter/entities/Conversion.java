package com.smartsoft.converter.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
public class Conversion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String sourceCurrency;
    private BigDecimal sourceAmount;
    private String targetCurrency;
    private BigDecimal targetAmount;
    private LocalDateTime convertedAt;

    @ManyToOne
    @JoinColumn(name = "rate_id")
    private Rate rate;


    public Conversion(String sourceCurrency, BigDecimal sourceAmount,
                      String targetCurrency, BigDecimal targetAmount,
                      LocalDateTime convertedAt, Rate rate) {
        this.sourceCurrency = sourceCurrency;
        this.sourceAmount = sourceAmount;
        this.targetCurrency = targetCurrency;
        this.targetAmount = targetAmount;
        this.convertedAt = convertedAt;
        this.rate = rate;
    }
}
