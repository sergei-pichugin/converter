package com.smartsoft.converter.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class HistoryItem {
    String sourceCode;
    String sourceAmount;
    String targetCode;
    String targetAmount;
    String date;

    public HistoryItem(String sourceCode, String sourceAmount, String targetCode, String targetAmount, String date) {
        this.sourceCode = sourceCode;
        this.sourceAmount = sourceAmount;
        this.targetCode = targetCode;
        this.targetAmount = targetAmount;
        this.date = date;
    }
}
