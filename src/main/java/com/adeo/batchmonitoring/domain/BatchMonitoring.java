package com.adeo.batchmonitoring.domain;

import lombok.Data;

import java.util.List;

/**
 * Objet principal du service de monitoring des batchs.
 * Une instance doit contenir l'ensemble des informations de monitoring des batchs.
 *
 * @author Nicolas Benizri
 */
@Data
public class BatchMonitoring {

    /**
     * Liste des lignes (jobs, steps informations) liées aux batchs monitorés.
     */
    private List<BatchMonitoringRow> batchMonitoringRows;

    /**
     * Contexte des batchs: "QSF", "Order Tracking", ...
     */
    private String context;
}
