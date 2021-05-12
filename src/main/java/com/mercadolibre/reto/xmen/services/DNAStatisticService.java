package com.mercadolibre.reto.xmen.services;

import com.mercadolibre.reto.xmen.domain.DNAStatistic;
import com.mercadolibre.reto.xmen.dto.DNAStatisticDTO;

import java.util.Optional;
import java.util.UUID;

public interface DNAStatisticService {
    DNAStatisticDTO getStatistics();

    Optional<DNAStatistic> getDnaStatistic(UUID dnaUUID);

    void saveDNAStatistic(DNAStatistic dnaStatistic);
}
