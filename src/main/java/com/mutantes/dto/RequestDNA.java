package com.mutantes.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * Representa una petición al servicio de validación de mutantes
 * @author sebastian
 *
 */
@Getter
@Setter
public class RequestDNA {
	
	private String[] dna;

}
