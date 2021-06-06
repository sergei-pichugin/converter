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

    private String sourceCode;
    private String sourceName;
    private BigDecimal sourceAmount;
    private String targetCode;
    private String targetName;
    private BigDecimal targetAmount;
    private LocalDateTime convertedAt;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "rate_id")
    private Rate rate;


    public Conversion(String sourceCode, String sourceName, BigDecimal sourceAmount,
                      String targetCode, String targetName, BigDecimal targetAmount,
                      LocalDateTime convertedAt, Rate rate) {
        this.sourceCode = sourceCode;
        this.sourceName = sourceName;
        this.sourceAmount = sourceAmount;
        this.targetCode = targetCode;
        this.targetName = targetName;
        this.targetAmount = targetAmount;
        this.convertedAt = convertedAt;
        this.rate = rate;
    }
}
