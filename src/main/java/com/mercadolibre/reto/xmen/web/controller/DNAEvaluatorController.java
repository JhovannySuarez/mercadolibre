package com.mercadolibre.reto.xmen.web.controller;

import com.mercadolibre.reto.xmen.dto.DNARequestDTO;
import com.mercadolibre.reto.xmen.exceptions.DNASequenceException;
import com.mercadolibre.reto.xmen.services.DNAEvaluatorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequestMapping("/mutant")
@RestController
public class DNAEvaluatorController {

    @Autowired
    private DNAEvaluatorService dnaEvaluatorService;

    @PostMapping("/")
    public ResponseEntity<?> adnEvaluation(@RequestBody @Validated DNARequestDTO requestDTO) {
        try {
            if (dnaEvaluatorService.isMutant(requestDTO)) {
                return new ResponseEntity<>(HttpStatus.OK);
            }
        } catch (DNASequenceException e) {
            log.debug(e.getMessage());
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }
}
