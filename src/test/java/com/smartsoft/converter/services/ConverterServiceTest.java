package com.smartsoft.converter.services;

import com.smartsoft.converter.dto.ConversionDto;
import com.smartsoft.converter.exceptions.ConversionDataException;
import com.smartsoft.converter.repositories.ConversionRepository;
import com.smartsoft.converter.repositories.RateRepository;
import org.dom4j.DocumentException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {ConverterServiceTest.Config.class})
public class ConverterServiceTest {

    @Autowired
    private ConverterService converterService;

    @MockBean
    private RateService rateService;

    @MockBean
    private HistoryService historyService;

    @MockBean
    private ConversionRepository conversionRepository;

    @MockBean
    private ResourceLoaderService resourceLoaderService;

    @Test
    void convert_notAllDataThrowsExceptionTest() {
        Assertions.assertThrows(ConversionDataException.class, () -> {
            converterService.convert(new ConversionDto(), Map.of());
        });
    }

    @Test
    void convert_amountsEqualForEqualCodes() throws ConversionDataException, IOException, DocumentException {
        ConversionDto conversionDto = new ConversionDto("USD", "20",
                "USD", null);
        Map<String, String> options = Map.of("USD", "dollars");
        converterService.convert(conversionDto, options);
        assertEquals("20", conversionDto.getTargetAmount());
    }

    @Configuration
    public static class Config {
        @Bean
        public ConverterService converterService() {
            return new ConverterService();
        }
    }
}