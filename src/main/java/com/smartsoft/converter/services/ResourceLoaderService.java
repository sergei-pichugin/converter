package com.smartsoft.converter.services;

import com.smartsoft.converter.entities.Rate;
import lombok.extern.slf4j.Slf4j;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Service("resourceLoaderService")
@Slf4j
public class ResourceLoaderService implements ResourceLoaderAware {
    private ResourceLoader resourceLoader;

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    public List<Rate> getResourceData() throws IOException, DocumentException {
        String rate_url = "http://www.cbr.ru/scripts/XML_daily.asp?date_req=" +
                LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        Resource resource = resourceLoader.getResource(rate_url);
        InputStream in = resource.getInputStream();
        SAXReader reader = new SAXReader();
        Document document = reader.read(in);
        log.info("document read");
        log.info(document.getRootElement().getName() + " for " +
                document.getRootElement().valueOf("@Date"));

        List<Node> nodes = document.selectNodes("/ValCurs/Valute" );
        List<Rate> rates = new LinkedList<>();

        for (Node node : nodes) {
            Rate rate = new Rate();
            rate.setValuteId(node.valueOf("@ID"));
            rate.setNumCode(Integer.valueOf(node.selectSingleNode("NumCode").getText()));
            rate.setCharCode(node.selectSingleNode("CharCode").getText());
            rate.setNominal(Integer.valueOf(node.selectSingleNode("Nominal").getText()));
            rate.setName(node.selectSingleNode("Name").getText());
            rate.setValue(new BigDecimal(node.selectSingleNode("Value").getText().replace(",", ".")));
            rate.setDate(LocalDate.parse(document.getRootElement().valueOf("@Date"),
                    DateTimeFormatter.ofPattern("dd.MM.yyyy")));
            rates.add(rate);
        }
        return rates;
    }

    public Map<String, String> getOptions() throws IOException, DocumentException {
        String rate_url = "http://www.cbr.ru/scripts/XML_daily.asp?date_req=" +
                LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        Resource resource = resourceLoader.getResource(rate_url);
        InputStream in = resource.getInputStream();
        SAXReader reader = new SAXReader();
        Document document = reader.read(in);

        List<Node> nodes = document.selectNodes("/ValCurs/Valute" );
        Map<String, String> options = new HashMap<>();

        for (Node node : nodes) {
            options.put(node.selectSingleNode("CharCode").getText(),
                    node.selectSingleNode("CharCode").getText() + " (" +
                    node.selectSingleNode("Name").getText() + ")");
        }
        return options;
    }

    public Map<String, String> getShortOptions() throws IOException, DocumentException {
        String rate_url = "http://www.cbr.ru/scripts/XML_daily.asp?date_req=" +
                LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        Resource resource = resourceLoader.getResource(rate_url);
        InputStream in = resource.getInputStream();
        SAXReader reader = new SAXReader();
        Document document = reader.read(in);

        List<Node> nodes = document.selectNodes("/ValCurs/Valute" );
        Map<String, String> options = new HashMap<>();

        for (Node node : nodes) {
            options.put(node.selectSingleNode("CharCode").getText(),
                    node.selectSingleNode("CharCode").getText());
        }
        return options;
    }
}
