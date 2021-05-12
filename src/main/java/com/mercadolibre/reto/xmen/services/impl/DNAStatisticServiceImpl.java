package com.mercadolibre.reto.xmen.services.impl;

import com.mercadolibre.reto.xmen.domain.DNAStatistic;
import com.mercadolibre.reto.xmen.dto.DNAStatisticDTO;
import com.mercadolibre.reto.xmen.repositories.DNAAnalysisRepository;
import com.mercadolibre.reto.xmen.services.DNAStatisticService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
public class DNAStatisticServiceImpl implements DNAStatisticService {

    @Autowired
    private DNAAnalysisRepository dnaAnalysisRepository;

    @Override
    public DNAStatisticDTO getStatistics() {
        long totalRows = dnaAnalysisRepository.count();
            long totalMutants = dnaAnalysisRepository.getMutantsCount();
        long totalHumans = totalRows - totalMutants;
        return DNAStatisticDTO.builder()
                .countHumanDNA(totalHumans)
                .countMutantDNA(totalMutants)
                .ratio(getRatio(totalHumans, totalMutants))
                .build();
    }

    @Override
    @Cacheable(cacheNames = "stats", key = "#analysisCode")
    public Optional<DNAStatistic> getDnaStatistic(UUID analysisCode) {
        log.debug("Getting statistics from database");
        return dnaAnalysisRepository.findById(analysisCode);
    }

    @Override
    public void saveDNAStatistic(DNAStatistic dnaStatistic){
        dnaAnalysisRepository.save(dnaStatistic);
    }

    private Double getRatio(long countHumanDNA, long countMutantDNA) {
        if (countHumanDNA > 0) {
            return ((double) countMutantDNA / countHumanDNA);
        }
        return countMutantDNA != 0 ? 1d : 0d;
    }
}
