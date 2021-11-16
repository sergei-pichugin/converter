package com.smartsoft.converter.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class HistoryItem {
    String sourceCode;
    String sourceName;
    String sourceAmount;
    String targetCode;
    String targetName;
    String targetAmount;
    String date;

    public HistoryItem(String sourceCode, String sourceName, String sourceAmount,
                       String targetCode, String targetName, String targetAmount,
                       String date) {
        this.sourceCode = sourceCode;
        this.sourceName = sourceName;
        this.sourceAmount = sourceAmount;
        this.targetCode = targetCode;
        this.targetName = targetName;
        this.targetAmount = targetAmount;
        this.date = date;
    }
}
