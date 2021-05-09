package com.mercadolibre.reto.xmen.services;

import com.mercadolibre.reto.xmen.dto.DNARequestDTO;
import com.mercadolibre.reto.xmen.exceptions.DNASequenceException;

public interface DNAEvaluatorService {
    boolean isMutant(DNARequestDTO dna) throws DNASequenceException;
}
