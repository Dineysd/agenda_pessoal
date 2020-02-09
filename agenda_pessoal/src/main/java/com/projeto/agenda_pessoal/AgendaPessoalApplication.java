package com.projeto.agenda_pessoal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.projeto.agenda_pessoal.config.property.AgendaApiProperty;

@SpringBootApplication
@EnableConfigurationProperties(AgendaApiProperty.class)
public class AgendaPessoalApplication {

	public static void main(String[] args) {
		SpringApplication.run(AgendaPessoalApplication.class, args);
	}

}
