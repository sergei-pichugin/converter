package com.smartsoft.converter.services;

import com.smartsoft.converter.entities.Rate;
import com.smartsoft.converter.repositories.RateRepository;
import org.dom4j.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RateService {

    @Autowired
    private RateRepository rateRepository;

    @Autowired
    ResourceLoaderService resourceLoaderService;

    @Transactional
    public Rate getLastRate(String code) throws IOException, DocumentException {
        LocalDate now = LocalDate.now();
        LocalDate lastDateInDb = rateRepository.findLastDate();
        if (now.equals(lastDateInDb) || now.isBefore(lastDateInDb)) {
            List<Rate> todayCodeRates = rateRepository.findByCharCodeAndDate(code, now);
            return todayCodeRates.get(0);
        }
        List<Rate> rates = resourceLoaderService.getResourceData();
        if (!lastDateInDb.equals(rates.get(0).getDate())) {
            rateRepository.saveAll(rates);
        }
        return rates.stream().filter(r -> code.equals(r.getCharCode())).collect(Collectors.toList()).get(0);
    }
}
