package com.smartsoft.converter;

import com.smartsoft.converter.entities.Rate;
import com.smartsoft.converter.repositories.RateRepository;
import com.smartsoft.converter.services.ResourceLoaderService;
import lombok.extern.slf4j.Slf4j;
import org.dom4j.DocumentException;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@SpringBootApplication
@Slf4j
public class ConverterApplication {


	public static void main(String[] args) {
		SpringApplication.run(ConverterApplication.class, args);
	}

	@Bean
	public CommandLineRunner dataLoader(RateRepository rateRepository,
										ResourceLoaderService resourceLoaderService) {
		return (args) -> {
			try {
				List<Rate> rates = resourceLoaderService.getResourceData();
				LocalDate lastDateInDb = rateRepository.findLastDate();
				if (lastDateInDb == null || !lastDateInDb.equals(rates.get(0).getDate())) {
					rateRepository.saveAll(rates);
				}
			} catch (IOException|DocumentException e) {
				log.error("Cannot get the latest data from the Central Bank - {}", e);
			}
		};
	}


}
