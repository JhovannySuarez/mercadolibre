package com.mercadolibre.reto.xmen.web.controller;

import com.mercadolibre.reto.xmen.dto.DNAStatisticDTO;
import com.mercadolibre.reto.xmen.services.DNAStatisticService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(DNAStatisticsController.class)
class DNAStatisticsControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private DNAStatisticService dnaStatisticService;

    @Test
    void getStatistics() throws Exception {
        given(dnaStatisticService.getStatistics()).willReturn(getDNAStatisticDTO());

        mockMvc.perform(get("/stats/" ).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    private DNAStatisticDTO getDNAStatisticDTO() {
        return DNAStatisticDTO.builder().countHumanDNA(100).countMutantDNA(40).build();
    }
}