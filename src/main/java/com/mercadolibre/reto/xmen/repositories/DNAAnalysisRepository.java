package com.mercadolibre.reto.xmen.repositories;

import com.mercadolibre.reto.xmen.domain.DNAStatistic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface DNAAnalysisRepository  extends JpaRepository<DNAStatistic, UUID> {

    @Query("SELECT COUNT(s) FROM DNAStatistic s WHERE s.isMutant = 1")
    long getMutantsCount();
}
