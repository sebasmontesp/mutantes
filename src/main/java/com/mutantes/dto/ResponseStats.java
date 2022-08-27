package com.mutantes.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseStats {
	
	@JsonProperty("count_mutant_dna")
	private Integer countMutantDna;
	
	@JsonProperty("count_human_dna")
	private Integer countHumanDna;
	
	@JsonProperty("ratio")
	private double ratio;
}
