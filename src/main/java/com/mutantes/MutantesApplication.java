package com.mutantes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Clase principal ejecutable
 * Por defecto toma la configuración del archivo applicaction.properties
 * @author sebastian
 *
 */
@SpringBootApplication
public class MutantesApplication {

	public static void main(String[] args) {
		SpringApplication.run(MutantesApplication.class, args);
	}

}
