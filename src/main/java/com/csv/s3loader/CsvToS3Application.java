package com.csv.s3loader;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CsvToS3Application {

	public static void main(String[] args) {
		SpringApplication.run(CsvToS3Application.class, args);
	}

}