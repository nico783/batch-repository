package com.adeo.batchmonitoring.domain;

import lombok.Data;

/**
 * Statistiques génériques d'une execution.
 *
 * @author Nicolas Benizri
 */
@Data
public class StatisticInfo {

    /**
     * Nombre de lecture réalisées pendant l'execution.
     */
    private Integer readCount;

    /**
     * Nombre d'écriture réalisées pendant l'execution.
     */
    private Integer writeCount;

    /**
     * Nombre de commits.
     */
    private Integer commitCount;
}
