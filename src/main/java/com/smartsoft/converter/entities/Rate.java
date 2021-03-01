package com.smartsoft.converter.entities;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Entity
public class Rate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String valuteId;
    private int numCode;
    private String charCode;
    private int nominal;
    private String name;
    private BigDecimal value;
    private LocalDate date;
}
