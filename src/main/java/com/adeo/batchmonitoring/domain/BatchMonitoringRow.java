package com.adeo.batchmonitoring.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;
import java.util.Objects;

/**
 * Entité liée à une execution de batch. Elle est définie à la maille "job" ou "step".
 * Nous supposons qu'un batch est constitué d'un ou plusieurs jobs eux-mêmes constitués
 * d'un ou plusieurs steps.
 * Une instance de BatchMonitoringRow de type "step" devrait donc toujours être liée
 * a un "job" et par conséquent contenir les deux informations
 * ({@link BatchMonitoringRow#stepInfo}, {@link BatchMonitoringRow#jobInfo}).
 * A l'inverse une instance de type "job" possède uniquement l'information {@link BatchMonitoringRow#jobInfo}.
 *
 * @author Nicolas Benizri
 */
@Data
@AllArgsConstructor
public class BatchMonitoringRow {

    /**
     * Informations relatives à l'execution d'un step.
     * Cet attribut peut-être null (caractéristique d'un monitoring à la maille "job").
     */
    private ExecutionInfo stepInfo;

    /**
     * Informations relatives à l'execution d'un job.
     * La maille job étant la moins fine, cet attribut devrait en principe toujours être renseigné.
     * Le cas d'un jobInfo null (avec ou sans stepInfo) a peu de sens.
     */
    private ExecutionInfo jobInfo;

    /**
     * Informations sur les statistiques d'executions.
     */
    private StatisticInfo statisticInfo;

    /**
     * Attribut permettant de renseigner des informations non génériques, propre a un context.
     * (Par exemple, le nom du fichier traité lors du passage du batch d'integration des commandes)
     */
    private Map<String, Object> customInfo;

    /**
     * Permet d'obtenir le type de monitoring.
     * Par convention le monitoring est de type step si {@link BatchMonitoringRow#stepInfo} n'est pas null.
     * Il est de type job sinon.
     *
     * @return le type de monitoring.
     */
    public MonitoringType getMonitoringType() {
        if (Objects.isNull(stepInfo)) {
            return MonitoringType.JOB;
        } else {
            return MonitoringType.STEP;
        }
    }
}
