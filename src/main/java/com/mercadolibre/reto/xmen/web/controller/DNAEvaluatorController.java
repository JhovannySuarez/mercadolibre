package com.mercadolibre.reto.xmen.web.controller;

import com.mercadolibre.reto.xmen.dto.DNARequestDTO;
import com.mercadolibre.reto.xmen.exceptions.DNASequenceException;
import com.mercadolibre.reto.xmen.services.DNAEvaluatorService;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequestMapping("/mutant")
@RestController
@Api(value = "Mutant Resource")
public class DNAEvaluatorController {


    @Autowired
    private DNAEvaluatorService dnaEvaluatorService;

    @ApiOperation(value = "Analyzes the ADN")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "In case a mutant is found in the ADN analysis"),
                    @ApiResponse(code = 403, message = "In other case")
            }
    )

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
