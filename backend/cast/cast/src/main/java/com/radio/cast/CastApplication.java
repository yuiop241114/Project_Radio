package com.radio.cast;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CastApplication {

	public static void main(String[] args) {
		SpringApplication.run(CastApplication.class, args);
	}

}
