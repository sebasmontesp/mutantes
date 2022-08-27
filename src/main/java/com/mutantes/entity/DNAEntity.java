package com.mutantes.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

/**
 * Mapeo ORM de la Tabla 'analisis' en postgresql
 * @author sebastian
 *
 */
@Getter
@Setter
@Entity
@Table(schema = "public", name = "analisis")
public class DNAEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_dna", columnDefinition = "serial", nullable = false, unique = true)
	private Integer idDna;
	
	@Column(name="dna", nullable = false, length = 100, unique = true)
	private String dna;
	
	@Column(name="result", nullable = false, length = 1)
	private String result;
	
}
