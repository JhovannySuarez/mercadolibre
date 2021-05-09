package com.mercadolibre.reto.xmen.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mercadolibre.reto.xmen.dto.DNARequestDTO;
import com.mercadolibre.reto.xmen.exceptions.DNASequenceException;
import com.mercadolibre.reto.xmen.services.DNAEvaluatorService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(DNAEvaluatorController.class)
class DNAEvaluatorControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    DNAEvaluatorService dnaEvaluatorService;

    @Test
    public void shouldResponseOKForMutantDNA() throws Exception {
        String[] dna = {"ATGCGA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG"};
        DNARequestDTO dnaRequestDTO = getDNARequestDTO(dna);

        String beerDtoJson = objectMapper.writeValueAsString(dnaRequestDTO);

        given(dnaEvaluatorService.isMutant(any())).willReturn(true);

        mockMvc.perform(post("/mutant/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(beerDtoJson))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldResponseForbiddenForHumanDNA() throws Exception {
        String[] dna = {"ATGCGA", "CAGTGC", "TTATTT", "AGACGG", "GCGTCA", "TCACTG"};
        DNARequestDTO dnaRequestDTO = getDNARequestDTO(dna);
        String beerDtoJson = objectMapper.writeValueAsString(dnaRequestDTO);

        given(dnaEvaluatorService.isMutant(any())).willReturn(false);

        mockMvc.perform(post("/mutant/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(beerDtoJson))
                .andExpect(status().isForbidden());
    }

    @Test()
    public void shouldResponseForbiddenForInvalidDNA()  throws Exception{
        String[] dna = {"ATGCGA", "CAGTGC", "TTATTT", "AGACGG", "GCGTCA", "TCACTG"};
        DNARequestDTO dnaRequestDTO = getDNARequestDTO(dna);
        String beerDtoJson = objectMapper.writeValueAsString(dnaRequestDTO);

        given(dnaEvaluatorService.isMutant(any())).willThrow( new DNASequenceException("Nitrogenous base not valid "));

        mockMvc.perform(post("/mutant/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(beerDtoJson))
                .andExpect(status().isForbidden());
    }


    private DNARequestDTO getDNARequestDTO(String[] dna) {
        return DNARequestDTO.builder().dna(dna).build();
    }

}