package com.smartsoft.converter.controllers;

import com.smartsoft.converter.dto.HistoryFilter;
import com.smartsoft.converter.dto.HistoryItem;
import com.smartsoft.converter.services.HistoryService;
import com.smartsoft.converter.services.ResourceLoaderService;
import lombok.extern.slf4j.Slf4j;
import org.dom4j.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@Slf4j
@SessionAttributes({"historyFilter", "shortOptions"})
public class HistoryController {

    @Autowired
    private ResourceLoaderService resourceLoaderService;

    @Autowired
    private HistoryService historyService;

    @ModelAttribute(name = "shortOptions")
    public Map<String, String> shortOptions() {
        Map<String, String> options = new HashMap<>();
        try {
            options = resourceLoaderService.getShortOptions();
            options.put("RUB", "RUB");   // рубль не входит в получаемый список
        } catch (IOException e) {
            log.error("Short options input/output problem: {}", e);
        } catch (DocumentException e) {
            log.error("Short options document problem: {}", e);
        }
        return options;
    }

    @ModelAttribute(name = "historyFilter")
    public HistoryFilter filter() {
        return new HistoryFilter(LocalDate.now(), "USD", "RUB");
    }

    @GetMapping("/history")
    public String history(
            HistoryFilter historyFilter,
            Model model) {
        List<HistoryItem> conversions = historyService.findByFilter(historyFilter);
        model.addAttribute("history", conversions);

        HistoryFilter modelFilter = (HistoryFilter)model.getAttribute("historyFilter");
        modelFilter.setDate(historyFilter.getDate());
        modelFilter.setSourceCode(historyFilter.getSourceCode());
        modelFilter.setTargetCode(historyFilter.getTargetCode());
        return "history";
    }
}
