package com.smartsoft.converter.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class HistoryFilter {
    private String date;
    private String sourceCode;
    private String targetCode;
}
