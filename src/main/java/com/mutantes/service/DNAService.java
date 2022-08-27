package com.mutantes.service;

import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mutantes.dto.ResponseStats;
import com.mutantes.entity.DNAEntity;
import com.mutantes.repository.DNARepository;
import com.mutantes.util.MatrixUtils;

@Service
public class DNAService {

	private final Logger logger = Logger.getLogger(DNAService.class.getName());

	@Autowired
	private DNARepository dnaRepository;

	private int minTamanoSecuencia = 4;
	private int minSecuencias = 2;

	public boolean isMutant(String[] dna) {
		boolean result = true;
		
		char[][] matrix = MatrixUtils.dnaToMatrix(dna);

		// Si hay mas de 1 secuencia se considera mutante
		int n = contarSecuenciasPorFilas(matrix);
		if (n < minSecuencias) {
			n += contarSecuenciasPorColumnas(matrix);

			if (n < minSecuencias) {
				n += contarSecuenciasPorDiagonal1(matrix);

				if (n < minSecuencias) {
					n += contarSecuenciasPorDiagonal2(matrix);

					if (n < minSecuencias) {
						result = false;
					}
				}

			}

		}

		try {
			DNAEntity entity = new DNAEntity();
			entity.setDna( Arrays.asList(dna).stream().collect(Collectors.joining()) );
			entity.setResult(result? "1" : "0");
			dnaRepository.save(entity);

		} catch (Exception e) {
			logger.log(Level.WARNING,e.getMessage(), e);

		}

		return result;
	}

	public ResponseStats getStats() {

		ResponseStats result = new ResponseStats();
		result.setCountHumanDna(dnaRepository.countByResult("0"));
		result.setCountMutantDna(dnaRepository.countByResult("1"));
		
		if(result.getCountHumanDna() > 0) {
			result.setRatio(result.getCountMutantDna() * 1.0 / result.getCountHumanDna());
		}

		return result;
	}

	/**
	 * Cuenta la cantidad de secuencias horizontalmente / por filas
	 * 
	 * @param dna
	 * @return
	 */
	public int contarSecuenciasPorFilas(char[][] matrix) {

		Integer numSecuencias = 0;

		for (int i = 0; i < matrix.length; i++) {
			numSecuencias += contarSecuencias(matrix[i]);

			logger.log(Level.FINE, "numSecuencias: {0}", numSecuencias);
		}

		return numSecuencias;
	}

	/**
	 * Cuenta la cantidad de secuencias verticalmente / por columnas
	 * 
	 * @param dna
	 * @return
	 */
	public int contarSecuenciasPorColumnas(char[][] matrix) {

		Integer numSecuencias = 0;

		for (int j = 0; j < matrix.length; j++) {

			char[] vectorFila = MatrixUtils.vectorColumna(matrix, j);

			numSecuencias += contarSecuencias(vectorFila);
		}

		return numSecuencias;
	}

	/**
	 * Cuenta la cantidad de secuencias diagonalmente (Diagonal principal)
	 * 
	 * @param dna
	 * @return
	 */
	public int contarSecuenciasPorDiagonal1(char[][] matrix) {

		Integer numSecuencias = 0;

		char[] vectorDiag1 = MatrixUtils.vectorDiag1(matrix);

		numSecuencias += contarSecuencias(vectorDiag1);

		return numSecuencias;
	}

	/**
	 * Cuenta la cantidad de secuencias diagonalmente (Diagonal secundaria)
	 * 
	 * @param dna
	 * @return
	 */
	public int contarSecuenciasPorDiagonal2(char[][] matrix) {

		Integer numSecuencias = 0;

		char[] vectorDiag2 = MatrixUtils.vectorDiag2(matrix);

		numSecuencias += contarSecuencias(vectorDiag2);

		return numSecuencias;
	}

	/**
	 * Método auxiliar que cuenta la cantidad de secuencias de tamaño minSecuencia
	 * 
	 * @param vector
	 * @param secuenceLength
	 * @param numSecuencias
	 * @return
	 */
	private Integer contarSecuencias(char[] vector) {
		Integer numSecuencias = 0;
		Integer secuenceLength = 1;

		for (int j = 0; j < vector.length - 1; j++) {
			char x1 = vector[j];
			char x2 = vector[j + 1];

			if (x1 == x2) {
				// Si 2 elementos consecutivos coinciden se considera una secuencia
				secuenceLength++;
				logger.log(Level.FINE, "secuenceLength: {0}", secuenceLength);
			} else {
				// Si 2 elementos consecutivos no coinciden se reinicia el tamaño de la
				// secuencia
				secuenceLength = 1;
			}

			if (secuenceLength == minTamanoSecuencia) {
				// Si el tamaño de la secuencia es el mínimo requerido se considera una
				// secuencia completa
				numSecuencias++;
			}
		}

		return numSecuencias;
	}

}
