package com.mutantes.util;

/**
 * Clase con funciones utilitarias relacionadas con el manejo de Matrices
 * @author sebastian
 *
 */
public class MatrixUtils {
	
	/**
	 * Determina si el contenido de una matriz es válido
	 * @param dna
	 * @return
	 */
	public static boolean isMatrixValid(String[] dna) {
		int n = dna.length;
		
		//Validar que no sea vacía
		if(n == 0) {
			return false;
		}
		
		for(String row : dna) {
			
			if(row == null) {
				//Validar que no sea nulo
				return false;
			}
			
			if(row.length() != n) {
				//Validar que sea de tamaño NxN
				return false;
			}
			
			if(! row.matches("[ATCG]+")) {
				//Validar caracteres A T C G
				return false;
			}
		}
		
		return true;
	}
	
	/**
	 * Convierte un arreglo de String en una matriz con un caracter por celda
	 * @param dna Arreglo de String
	 * @return
	 */
	public static char[][] dnaToMatrix(String[] dna) {
		int n = dna.length;
		char[][] result = new char[n][n];
		
		for(int i = 0; i < n; i++) {
			result[i] = dna[i].toCharArray();
		}
		
		return result;
	}
	
	/**
	 * Método auxiliar para obtener un vector columna a partir de una matriz
	 * @param matrix
	 * @param j Número de columna a obtener (base 0)
	 * @return
	 */
	public static char[] vectorColumna(char[][] matrix, int j) {
		char[] vector = new char[matrix.length];
		
		for(int i = 0; i < matrix.length; i++) {
			vector[i] = matrix[i][j];
		}
		
		return vector;
	}
	
	/**
	 * Método auxiliar para obtener un vector diagonal principal a partir de una matriz
	 * @param matrix
	 * @return
	 */
	public static char[] vectorDiag1(char[][] matrix) {
		char[] vector = new char[matrix.length];
		
		for(int i = 0; i < matrix.length; i++) {
			vector[i] = matrix[i][i];
		}
		
		return vector;
	}
	
	/**
	 * Método auxiliar para obtener un vector diagonal secundaria a partir de una matriz
	 * @param matrix
	 * @return
	 */
	public static char[] vectorDiag2(char[][] matrix) {
		char[] vector = new char[matrix.length];
		
		for(int k = 0; k < matrix.length; k++) {
			int i = matrix.length - k - 1;
			
			vector[k] = matrix[i][k];
		}
		
		return vector;
	}

}
