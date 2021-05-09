package com.mercadolibre.reto.xmen;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RetoXmenApplication {

	public static void main(String[] args) {
		SpringApplication.run(RetoXmenApplication.class, args);
	}

//	@Override
//	public void run(String... args) throws Exception {
//		//String[] dna = {"ATGCGA","CAGTGC","TTATGT","AGAAGG","CCCCTA","TCACTG"};
//		//String[] dna = {"ATGCGA","CAGTGC","TTATTT","AGACGG","GCGTCA","TCACTG"};
//		//String[] dna = {"ATGCGA","AAGTGC","ATATTG","AGACGG","GCGTCG","TCACTG"};
//		String[] dna = {"XAAAUA","AAGTGC","ATATTG","AGACGG","GCGTCG","TCACTT"};
//		DNAEvaluatorServiceImpl dnaEvaluatorService = new DNAEvaluatorServiceImpl();
//		System.out.println("Is mutant  :::::   " + dnaEvaluatorService.isMutant(dna));
//	}
}
