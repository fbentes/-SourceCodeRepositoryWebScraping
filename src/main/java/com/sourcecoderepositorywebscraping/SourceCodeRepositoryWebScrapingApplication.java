package com.sourcecoderepositorywebscraping;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class SourceCodeRepositoryWebScrapingApplication {

	public static void main(String[] args) {
		SpringApplication.run(SourceCodeRepositoryWebScrapingApplication.class, args);
	}
}
