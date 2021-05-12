package com.mercadolibre.reto.xmen;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class RetoXmenApplication {

	public static void main(String[] args) {
		SpringApplication.run(RetoXmenApplication.class, args);
	}

}
