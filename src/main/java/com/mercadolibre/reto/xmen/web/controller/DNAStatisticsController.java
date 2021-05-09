package com.mercadolibre.reto.xmen.web.controller;

import com.mercadolibre.reto.xmen.dto.DNAStatisticDTO;
import com.mercadolibre.reto.xmen.services.DNAStatisticService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequestMapping("/stats")
@RestController
public class DNAStatisticsController {

    @Autowired
    private DNAStatisticService dnaStatisticService;

    @GetMapping("/")
    public ResponseEntity<DNAStatisticDTO> getStatistics() {

        DNAStatisticDTO dnaStatisticDTO =  dnaStatisticService.getStatistics();
        return new ResponseEntity<>(dnaStatisticDTO, HttpStatus.OK);
    }
}
