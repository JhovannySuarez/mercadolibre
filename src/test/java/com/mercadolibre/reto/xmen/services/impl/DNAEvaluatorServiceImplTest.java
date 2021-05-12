package com.mercadolibre.reto.xmen.services.impl;

import com.mercadolibre.reto.xmen.domain.DNAStatistic;
import com.mercadolibre.reto.xmen.dto.DNARequestDTO;
import com.mercadolibre.reto.xmen.exceptions.DNASequenceException;
import com.mercadolibre.reto.xmen.repositories.DNAAnalysisRepository;
import com.mercadolibre.reto.xmen.services.DNAEvaluatorService;
import com.mercadolibre.reto.xmen.services.DNAStatisticService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@RunWith(SpringRunner.class)
class DNAEvaluatorServiceImplTest {

    @MockBean
    private DNAAnalysisRepository dnaAnalysisRepository;

    @MockBean
    private DNAStatisticService dnaStatisticService;

    @Autowired
    private DNAEvaluatorService dnaEvaluatorService;

    @Test
    void testHumanADN() throws DNASequenceException {
        String[] dna = {"ATGCGA", "CAGTGC", "TTATTT", "AGACGG", "GCGTCA", "TCACTG"};
        DNARequestDTO dnaRequestDTO = getDNARequestDTO(dna);

        assertThat(dnaEvaluatorService.isMutant(dnaRequestDTO))
                .isEqualTo(false);

    }

    @Test
    void testMutantADN() throws DNASequenceException {
        String[] dna = {"ATGCGA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG"};
        DNARequestDTO dnaRequestDTO = getDNARequestDTO(dna);

        assertThat(dnaEvaluatorService.isMutant(dnaRequestDTO))
                .isEqualTo(true);
    }

    @Test
    void testMutantHorizontalSubSequence() throws DNASequenceException  {
        String[] dna = {"AAAAGA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG"};
        DNARequestDTO dnaRequestDTO = getDNARequestDTO(dna);

        assertThat(dnaEvaluatorService.isMutant(dnaRequestDTO))
                .isEqualTo(true);
    }

    @Test
    void testObliquelyLeftSubSequence() throws DNASequenceException  {
        String[] dna = {"CAAAGA", "CAGTGC", "CTAGGT", "TGGAGG", "AGCCTA", "TCACTG"};
        DNARequestDTO dnaRequestDTO = getDNARequestDTO(dna);

        assertThat(dnaEvaluatorService.isMutant(dnaRequestDTO))
                .isEqualTo(true);
    }

    @Test
    void testNullSubSequence() throws DNASequenceException  {
        String[] dna = {null, "CAGTGC", "CTAGGT", "TGGAGG", "AGCCTA", "TCACTG"};
        DNARequestDTO dnaRequestDTO = getDNARequestDTO(dna);

        assertThat(dnaEvaluatorService.isMutant(dnaRequestDTO))
                .isEqualTo(false);
    }

    @Test
    void testUncompletedSubSequence() throws DNASequenceException  {
        String[] dna = {null, "CGTGC", "CTAGGT", "TGGAGG", "AGCCTA", "TCACTG"};
        DNARequestDTO dnaRequestDTO = getDNARequestDTO(dna);

        assertThat(dnaEvaluatorService.isMutant(dnaRequestDTO))
                .isEqualTo(false);
    }

    @Test
    void testInvalidSubSequence()  {
        String[] dna = {null, "CGTGX", "CTAGGT", "TGGAGG", "AGCCTA", "TCACTG"};
        DNARequestDTO dnaRequestDTO = getDNARequestDTO(dna);

        Assertions.assertThrows(DNASequenceException.class, () -> dnaEvaluatorService.isMutant(dnaRequestDTO));
    }

    private DNARequestDTO getDNARequestDTO(String[] dna) {
        return DNARequestDTO.builder().dna(dna).build();
    }

    private DNAStatistic getDNAStatistic(boolean isMutant) {
        return DNAStatistic.builder().isMutant(isMutant).build();
    }

}