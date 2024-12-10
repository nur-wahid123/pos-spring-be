package com.pos.point_of_sale;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.github.cdimascio.dotenv.Dotenv;

@SpringBootApplication
public class PointOfSaleApplication {

	public static void main(String[] args) {
		Dotenv dotenv = Dotenv.configure().directory("./.env").load();
		dotenv.entries().forEach(entry -> System.setProperty(entry.getKey(), entry.getValue()));
		SpringApplication.run(PointOfSaleApplication.class, args);
	}

}
