package com.smartsoft.converter.services;

import com.smartsoft.converter.dto.HistoryFilter;
import com.smartsoft.converter.dto.HistoryItem;
import com.smartsoft.converter.entities.Conversion;
import com.smartsoft.converter.repositories.ConversionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;

@Service
public class HistoryService {

    @Autowired
    private ConversionRepository conversionRepository;

    public List<HistoryItem> findByFilter(HistoryFilter historyFilter) {
        List<Conversion> conversions;
        if (historyFilter.getDate() != null) {
            conversions = findByCurrencyAndDate(historyFilter);
        } else {
            conversions = findByCurrency(historyFilter);
        }
        List<HistoryItem> history = new LinkedList<>();
        conversions.forEach(c -> history.add(
                new HistoryItem(c.getSourceCode(), c.getSourceName(), c.getSourceAmount().toString(),
                c.getTargetCode(), c.getTargetName(), c.getTargetAmount().toString(),
                c.getConvertedAt().toLocalDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")))));
        return history;
    }

    private List<Conversion> findByCurrencyAndDate(HistoryFilter historyFilter) {
        LocalDate date = historyFilter.getDate();
        return conversionRepository.findByStartEndAndConvertedAtBetweenOrderByConvertedAtDescIdAsc(
                historyFilter.getSourceCode() + "_" + historyFilter.getTargetCode(),
                date.atTime(0, 0, 0), date.atTime(23, 59, 59));
    }

    private List<Conversion> findByCurrency(HistoryFilter historyFilter) {
        return conversionRepository.findByStartEndOrderByConvertedAtDescIdAsc(
                historyFilter.getSourceCode() + "_" + historyFilter.getTargetCode());
    }
}
