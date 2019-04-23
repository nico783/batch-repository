package com.adeo.batchmonitoring.service;

import com.adeo.batchmonitoring.domain.BatchMonitoring;
import com.adeo.batchmonitoring.domain.BatchMonitoringRow;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Service central devant être implémenté dans l'application cliente.
 * Permet de récupérer un objet {@link BatchMonitoring}.
 *
 * @author Nicolas Benizri
 */
public abstract class BatchMonitoringService {

    /**
     * Méthode de récupération d'une entité {@link BatchMonitoring} permettant le monitoring de l'ensemble des batchs.
     * Son fonctionnement nécessite de définir les méthodes abstraites ci-dessous (pattern template).
     *
     * @return instance du monitoring des batchs.
     */
    public final BatchMonitoring getBatchMonitoring() {
        return getBatchMonitoring(batchMonitoringRow -> true);
    }

    /**
     * Méthode de récupération d'une entité {@link BatchMonitoring} avec l'application un filtre.
     * Son fonctionnement nécessite de définir les méthodes abstraites ci-dessous (pattern template).
     *
     * @return instance du monitoring des batchs.
     */
    public final BatchMonitoring getBatchMonitoring(Predicate<BatchMonitoringRow> predicate) {
        BatchMonitoring batchMonitoring = new BatchMonitoring();
        List<? extends BatchMonitoringRow> batchMonitoringRows = findBatchMonitoringRows()
                .stream()
                .filter(predicate)
                .collect(Collectors.toList());
        batchMonitoring.setBatchMonitoringRows(new ArrayList<>(batchMonitoringRows));
        batchMonitoring.setContext(context());
        return batchMonitoring;
    }

    /**
     * Stratégie de récupération de la liste des {@link BatchMonitoringRow}.
     *
     * @return liste des lignes de monitoring.
     */
    protected abstract List<? extends BatchMonitoringRow> findBatchMonitoringRows();

    /**
     * A implémenter pour définir le contexte de monitoring {@link BatchMonitoring#getContext()}.
     * @return le contexte du monitoring
     */
    protected abstract String context();
}
