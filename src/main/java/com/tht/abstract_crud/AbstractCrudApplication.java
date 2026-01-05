package com.tht.abstract_crud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class AbstractCrudApplication {

	public static void main(String[] args) {
		SpringApplication.run(AbstractCrudApplication.class, args);
	}

}
