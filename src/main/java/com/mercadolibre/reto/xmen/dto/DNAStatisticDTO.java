package com.mercadolibre.reto.xmen.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DNAStatisticDTO {

    @JsonProperty("count_mutant_dna")
    private long countMutantDNA;

    @JsonProperty("count_human_dna")
    private long countHumanDNA;

    private Double ratio;

}
