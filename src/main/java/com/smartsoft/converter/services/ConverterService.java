package com.smartsoft.converter.services;

import com.smartsoft.converter.dto.ConversionDto;
import com.smartsoft.converter.entities.Conversion;
import com.smartsoft.converter.entities.Rate;
import com.smartsoft.converter.exceptions.ConversionDataException;
import com.smartsoft.converter.repositories.ConversionRepository;
import org.dom4j.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

@Service
public class ConverterService {

    @Autowired
    private RateService rateService;

    @Autowired
    private HistoryService historyService;

    @Autowired
    private ConversionRepository conversionRepository;

    @Autowired
    private ResourceLoaderService resourceLoaderService;

    public boolean convert(ConversionDto conversionDto)
            throws ConversionDataException, IOException, DocumentException {
        validate(conversionDto);

        if (conversionDto.getSourceCode().equals(conversionDto.getTargetCode())) {
            BigDecimal result = convertToTheSameCode(conversionDto.getSourceCode(), conversionDto.getSourceAmount());
            conversionDto.setTargetAmount(result.toString());
            return true;
        } else if ("RUB".equals(conversionDto.getSourceCode())) {
            BigDecimal result = convertFromRub(conversionDto.getSourceAmount(), conversionDto.getTargetCode());
            conversionDto.setTargetAmount(result.toString());
            return true;
        } else if ("RUB".equals(conversionDto.getTargetCode())) {
            BigDecimal result = convertToRub(conversionDto.getSourceCode(), conversionDto.getSourceAmount());
            conversionDto.setTargetAmount(result.toString());
            return true;
        } else {
            BigDecimal rub = convertToRub(conversionDto.getSourceCode(), conversionDto.getSourceAmount());
            BigDecimal result = convertFromRub(rub.toString(), conversionDto.getTargetCode());
            conversionDto.setTargetAmount(result.toString());
            return true;
        }
    }

    private BigDecimal convertToRub(String sourceCode, String sourceAmount) throws IOException, DocumentException {
        Rate rate = rateService.getLastRate(sourceCode);
        BigDecimal fromCash = new BigDecimal(sourceAmount);
        BigDecimal result = fromCash.multiply(rate.getValue()).multiply(BigDecimal.valueOf(rate.getNominal()));
        Conversion conversion = new Conversion(sourceCode,
                fromCash,
                "RUB",
                result,
                LocalDateTime.now(), rate);
        conversionRepository.save(conversion);
        return result;
    }

    private BigDecimal convertFromRub(String sourceAmount, String targetCode) throws IOException, DocumentException {
        Rate rate = rateService.getLastRate(targetCode);
        BigDecimal rub = new BigDecimal(sourceAmount);
        BigDecimal result =  rub
                .divide(rate.getValue(), 4, RoundingMode.HALF_UP)
                .divide(BigDecimal.valueOf(rate.getNominal()), 4, RoundingMode.HALF_UP);
        Conversion conversion = new Conversion("RUB",
                rub,
                targetCode,
                result,
                LocalDateTime.now(), rate);
        conversionRepository.save(conversion);
        return result;
    }

    private BigDecimal convertToTheSameCode(String sourceCode, String sourceAmount) {
        Conversion conversion = new Conversion(sourceCode,
                new BigDecimal(sourceAmount),
                sourceCode,
                new BigDecimal(sourceAmount),
                LocalDateTime.now(), null);
        conversionRepository.save(conversion);
        return conversion.getTargetAmount();
    }

    private void validate(ConversionDto conversionDto) throws ConversionDataException {
        if (conversionDto.getSourceCode() == null || conversionDto.getSourceAmount() == null ||
                conversionDto.getTargetCode() == null) {
            throw new ConversionDataException("Не все данные для конверсии");
        }
        try {
            new BigDecimal(conversionDto.getSourceAmount());
        } catch (NumberFormatException e) {
            throw new ConversionDataException("Исходная сумма не читается как число");
        }

    }

}
