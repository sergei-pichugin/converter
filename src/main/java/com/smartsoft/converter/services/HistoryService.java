package com.smartsoft.converter.services;

import com.smartsoft.converter.dto.HistoryFilter;
import com.smartsoft.converter.dto.HistoryItem;
import com.smartsoft.converter.entities.Conversion;
import com.smartsoft.converter.repositories.ConversionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
        conversions.forEach(c -> {
            history.add(new HistoryItem(c.getSourceCurrency(), c.getSourceAmount().toString(),
                    c.getTargetCurrency(), c.getTargetAmount().toString(),
                    c.getConvertedAt().toLocalDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))));
        });
        return history;
    }

    private List<Conversion> findByCurrencyAndDate(HistoryFilter historyFilter) {
        LocalDate date = LocalDate.parse(historyFilter.getDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        if ("RUB".equals(historyFilter.getSourceCode()) ||
                "RUB".equals(historyFilter.getTargetCode()) ||
                historyFilter.getSourceCode().equals(historyFilter.getTargetCode())) {
            return conversionRepository.findBySourceCurrencyAndTargetCurrencyAndConvertedAtBetween(
                    historyFilter.getSourceCode(), historyFilter.getTargetCode(),
                    date.atTime(0, 0, 0), date.atTime(23, 59, 59));
        }
        List<Conversion> conversions = conversionRepository.findBySourceCurrencyAndTargetCurrencyAndConvertedAtBetween(
                historyFilter.getSourceCode(), "RUB",
                date.atTime(0, 0, 0), date.atTime(23, 59, 59));
        List<Conversion> fromRub = conversionRepository.findBySourceCurrencyAndTargetCurrencyAndConvertedAtBetween(
                "RUB", historyFilter.getTargetCode(),
                date.atTime(0, 0, 0), date.atTime(23, 59, 59));

        conversions.addAll(fromRub);
        return conversions;
    }

    private List<Conversion> findByCurrency(HistoryFilter historyFilter) {
        if ("RUB".equals(historyFilter.getSourceCode()) ||
                "RUB".equals(historyFilter.getTargetCode()) ||
                historyFilter.getSourceCode().equals(historyFilter.getTargetCode())) {
            return conversionRepository.findBySourceCurrencyAndTargetCurrency(
                    historyFilter.getSourceCode(), historyFilter.getTargetCode());
        }
        List<Conversion> conversions = conversionRepository.findBySourceCurrencyAndTargetCurrency(
                historyFilter.getSourceCode(), "RUB");
        List<Conversion> fromRub = conversionRepository.findBySourceCurrencyAndTargetCurrency(
                "RUB", historyFilter.getTargetCode());

        conversions.addAll(fromRub);
        return conversions;
    }
}
