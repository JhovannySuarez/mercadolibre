package com.mercadolibre.reto.xmen.services.impl;

import com.mercadolibre.reto.xmen.dto.DNAStatisticDTO;
import com.mercadolibre.reto.xmen.repositories.DNAAnalysisRepository;
import com.mercadolibre.reto.xmen.services.DNAStatisticService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@RunWith(SpringRunner.class)
class DNAStatisticServiceImplTest {

    @MockBean
    private DNAAnalysisRepository dnaAnalysisRepository;

    @Autowired
    private DNAStatisticService dnaStatisticService;

    @Test
    void testHappyPath()  {
        Mockito.when(dnaAnalysisRepository.count())
                .thenReturn(140L);
        Mockito.when(dnaAnalysisRepository.getMutantsCount())
                .thenReturn(40L);

        DNAStatisticDTO dnaStatisticDTO = dnaStatisticService.getStatistics();

        assertThat(dnaStatisticDTO.getCountHumanDNA())
        .isEqualTo(100);

        assertThat(dnaStatisticDTO.getCountMutantDNA())
                .isEqualTo(40);

        assertThat(dnaStatisticDTO.getRatio())
                .isEqualTo(0.4);
    }

    @Test
    void testWithZeroHumans()  {
        Mockito.when(dnaAnalysisRepository.count())
                .thenReturn(100L);
        Mockito.when(dnaAnalysisRepository.getMutantsCount())
                .thenReturn(100L);

        DNAStatisticDTO dnaStatisticDTO = dnaStatisticService.getStatistics();

        assertThat(dnaStatisticDTO.getCountHumanDNA())
                .isEqualTo(0);

        assertThat(dnaStatisticDTO.getCountMutantDNA())
                .isEqualTo(100);

        assertThat(dnaStatisticDTO.getRatio())
                .isEqualTo(1.0);
    }


}