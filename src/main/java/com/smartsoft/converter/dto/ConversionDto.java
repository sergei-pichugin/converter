package com.smartsoft.converter.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConversionDto {
    private String sourceCode;
    private String sourceAmount;
    private String targetCode;
    private String targetAmount;
}
