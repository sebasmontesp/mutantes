package com.mutantes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.mutantes.dto.RequestDNA;
import com.mutantes.dto.ResponseStats;
import com.mutantes.service.DNAService;
import com.mutantes.util.MatrixUtils;

/**
 * Servicios REST relacionados con análisis de ADN 
 * @author sebastian montes
 *
 */
@RestController
@CrossOrigin("*")
public class DNAController {
	@Autowired
	private DNAService dnaService;
	
	@PostMapping(path="mutant", produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<String> isMutant(@RequestBody RequestDNA request) {
		
		try {
			boolean isValido = MatrixUtils.isMatrixValid(request.getDna());
			if(!isValido) {
				return ResponseEntity.status(HttpStatus.FORBIDDEN).body("ERROR");
			}

			boolean isMutant = dnaService.isMutant(request.getDna());

			if(isMutant) {
				return ResponseEntity.status(HttpStatus.OK).body("OK");
			}

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("ERROR");
		}

		return ResponseEntity.status(HttpStatus.FORBIDDEN).body("");
	}
	
	@GetMapping(path="stats", produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<ResponseStats> stats() {

		try {
			ResponseStats stats = dnaService.getStats();
			
			return ResponseEntity.status(HttpStatus.OK).body(stats);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseStats());
		}

	}
	
}
