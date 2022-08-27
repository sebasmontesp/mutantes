package com.mutantes.test;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.mutantes.dto.ResponseStats;
import com.mutantes.repository.DNARepository;
import com.mutantes.service.DNAService;
import com.mutantes.util.MatrixUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class MutantesApplicationTests {

	@InjectMocks
	private DNAService dnaService;
	
	@Mock
	private DNARepository dnaRepository;
	
	/**
	 * Debe validar si la matriz ens válida:
	 * No vacía,
	 * Tamaño NxN
	 * Solo caracteres A C T G
	 */
	@Test
	void testIsValidMatrixCase01() {
		String[] dna1 = new String[0];
		String[] dna2a = new String[] {""};
		String[] dna2b = new String[] {"AA", null};
		String[] dna3 = new String[] {"AA","C"};
		String[] dna4 = new String[] {"AB", "CD"};
		String[] dna5 = new String[] {"AAA", "CCC", "TTT"};
		String[] dna6 = new String[] {"AAAA", "CCCC", "TTTT", "GGGG"};

		boolean isValido = MatrixUtils.isMatrixValid(dna1);
		assertFalse(isValido);
		
		isValido = MatrixUtils.isMatrixValid(dna2a);
		assertFalse(isValido);
		
		isValido = MatrixUtils.isMatrixValid(dna2b);
		assertFalse(isValido);
		
		isValido = MatrixUtils.isMatrixValid(dna3);
		assertFalse(isValido);
		
		isValido = MatrixUtils.isMatrixValid(dna4);
		assertFalse(isValido);
		
		isValido = MatrixUtils.isMatrixValid(dna5);
		assertTrue(isValido);
		
		isValido = MatrixUtils.isMatrixValid(dna6);
		assertTrue(isValido);

	}

	/**
	 * Debe retornar la cantidad de secuencias de N elemenos iguales evaluando por
	 * filas
	 */
	@Test
	void testIsMutantCase01_Filas() {
		String[] dna1 = new String[] { "ATGCGA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG" };
		String[] dna2 = new String[] { "ATGCGA", "CAGTGC", "TTATGT", "AAAAGG", "CCCCTA", "TCACTG" };
		String[] dna3 = new String[] { "ATGCGA", "CAGTGC", "TTATGT", "AAAAGG", "CCCCTA", "TTTTTG" };

		int subTotal = 0;

		subTotal = dnaService.contarSecuenciasPorFilas(MatrixUtils.dnaToMatrix(dna1));
		assertEquals(1, subTotal);

		subTotal = dnaService.contarSecuenciasPorFilas(MatrixUtils.dnaToMatrix(dna2));
		assertEquals(2, subTotal);

		subTotal = dnaService.contarSecuenciasPorFilas(MatrixUtils.dnaToMatrix(dna3));
		assertEquals(3, subTotal);
	}

	/**
	 * Debe retornar la cantidad de secuencias de 4 elemenos iguales evaluando por
	 * columnas
	 */
	@Test
	void testIsMutantCase02_Columnas() {
		String[] dna1 = new String[] { "ATGCGA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG" };
		String[] dna2 = new String[] { "ATGCGA", "CTGTGC", "TTATGT", "ATAAGG", "CTCCTA", "ACACTG" };
		String[] dna3 = new String[] { "ATGCCA", "AAGTGC", "ATGTCT", "AGGACG", "CCCGCA", "TCACCG" };

		int subTotal = 0;

		subTotal = dnaService.contarSecuenciasPorColumnas(MatrixUtils.dnaToMatrix(dna1));
		assertEquals(1, subTotal);

		subTotal = dnaService.contarSecuenciasPorColumnas(MatrixUtils.dnaToMatrix(dna2));
		assertEquals(2, subTotal);

		subTotal = dnaService.contarSecuenciasPorColumnas(MatrixUtils.dnaToMatrix(dna3));
		assertEquals(3, subTotal);
	}

	/**
	 * Debe retornar la cantidad de secuencias de 4 elemenos iguales evaluando por
	 * diagonales
	 */
	@Test
	void testIsMutantCase03_Diagonales() {
		String[] dna1 = new String[] { "ATGCGA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG" };
		String[] dna2 = new String[] { "ATGCGT", "CTGTTC", "TTATGT", "ATTAGG", "CTCCTA", "ACACTG" };

		int subTotal = 0;

		subTotal = dnaService.contarSecuenciasPorDiagonal1(MatrixUtils.dnaToMatrix(dna1));
		assertEquals(1, subTotal);

		subTotal = dnaService.contarSecuenciasPorDiagonal2(MatrixUtils.dnaToMatrix(dna2));
		assertEquals(1, subTotal);
	}
	
	/**
	 * Debe determinar que es mutante si: Hay más de una secuencia de cuatro letras iguales​, de forma oblicua, horizontal o vertical.
	 */
	@Test
	void testIsMutantCase04_Matrix() {
		String[] dna1 = new String[] { "ATGCGA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG" };//Secuencia horizontal, vertical y oblicua
		String[] dna2 = new String[] { "ATGCGA", "CAGTGC", "TTATCT", "AGAAGG", "CCCCTA", "TCACTG" };//Secuencia horizontal y oblicua
		String[] dna3 = new String[] { "ATGCGA", "CAGTGC", "TTATGT", "AGACGG", "CCCCTA", "TCACTG" };//Secuencia vertical y oblicua
		String[] dna4 = new String[] { "AAAACT", "GCCCCA", "GCTTTT", "AGAAGG", "CACCTA", "TCACTG" };//3 Secuencias horizontales
		String[] dna5 = new String[] { "ATGCTA", "CAGTGC", "TTATGT", "AGAAGG", "CACCTA", "TCACTG" };//1 Secuencia
		String[] dna6 = new String[] { "ATGCTA", "CAGTGC", "TTCTGT", "AGAAGG", "CACCTA", "TCACTG" };//Sin Secuencias

		boolean esMutante = dnaService.isMutant(dna1);
		assertTrue(esMutante);
		
		esMutante = dnaService.isMutant(dna2);
		assertTrue(esMutante);
		
		esMutante = dnaService.isMutant(dna3);
		assertTrue(esMutante);
		
		esMutante = dnaService.isMutant(dna4);
		assertTrue(esMutante);
		
		esMutante = dnaService.isMutant(dna5);
		assertFalse(esMutante);
		
		esMutante = dnaService.isMutant(dna6);
		assertFalse(esMutante);

	}
	
	@Test
	void testStatsCase01() {
		ResponseStats stats = dnaService.getStats();

		assertNotNull(stats);
		assertNotNull(stats.getCountHumanDna());
		assertNotNull(stats.getCountMutantDna());
		assertTrue(stats.getCountMutantDna() >= 0d);
		assertTrue(stats.getCountHumanDna() >= 0d);
		assertTrue(stats.getRatio() >= 0d);

		if(stats.getCountHumanDna() > 0) {
			assertEquals(stats.getCountMutantDna() * 1.0 / stats.getCountHumanDna(), stats.getRatio());
		} else {
			assertEquals(0d, stats.getRatio());
		}

	}

}
