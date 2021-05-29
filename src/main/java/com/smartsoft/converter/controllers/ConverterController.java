package com.smartsoft.converter.controllers;

import com.smartsoft.converter.dto.ConversionDto;
import com.smartsoft.converter.services.ConverterService;
import com.smartsoft.converter.services.RateService;
import com.smartsoft.converter.services.ResourceLoaderService;
import lombok.extern.slf4j.Slf4j;
import org.dom4j.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.io.IOException;
import java.util.Map;

@Controller
@Slf4j
@SessionAttributes({"conversion", "options"})
public class ConverterController {

    @Autowired
    private ResourceLoaderService resourceLoaderService;

    @Autowired
    private ConverterService converterService;

    @Autowired
    private RateService rateService;

    @ModelAttribute(name = "conversion")
    public ConversionDto conversion() {
        return new ConversionDto();
    }

    @ModelAttribute(name = "options")
    public Map<String, String> options() {
        Map<String, String> options = null;
        try {
            options = resourceLoaderService.getOptions();
            options.put("RUB", "RUB (Российский рубль)");   // рубль не входит в получаемый список
        } catch (IOException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return options;
    }
    
    @GetMapping("/")
    public String root(Model model) {
        return "redirect:/main";
    }

    @GetMapping("/main")
    public String mainForm(Model model) {
        return "main";
    }

    @PostMapping("/convert")
    public String convert(ConversionDto conversion, Model model) {    //conversion is from the view
        log.debug("Converting: {}", conversion);
        if (conversion == null) return "redirect:/main";
        try {
            converterService.convert(conversion);
        } catch (Exception e) {
            model.addAttribute("message", e.getMessage());
            log.error("Exception when converting: {}", e);
            return "error";
        }
        log.debug("Conversion done: {}", conversion);
        // now view conversion goes to model
        ConversionDto modelConversion = (ConversionDto)model.getAttribute("conversion");
        modelConversion.setSourceCode(conversion.getSourceCode());
        modelConversion.setSourceAmount(conversion.getSourceAmount());
        modelConversion.setTargetCode(conversion.getTargetCode());
        modelConversion.setTargetAmount(conversion.getTargetAmount());
        return "redirect:/main";
    }
}
