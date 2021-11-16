package com.smartsoft.converter.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class HistoryFilter {

    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private LocalDate date;
    private String sourceCode;
    private String targetCode;
}
