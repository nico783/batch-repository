package com.adeo.batchmonitoring.domain;

import lombok.Data;

import java.util.Date;

/**
 * Informations relatives a une execution (job ou step) lors d'un batch.
 *
 * @author Nicolas Benizri
 */
@Data
public class ExecutionInfo {
    /**
     * Identifiant technique.
     */
    private Long id;

    /**
     * Nom.
     */
    private String name;

    /**
     * Date de démarrage de l'execution.
     */
    private Date start;

    /**
     * Date de fin de l'execution.
     */
    private Date end;

    /**
     * Status de l'execution.
     */
    private String status;

    /**
     * Message delivré en fin d'execution (exception message en cas d'erreur).
     */
    private String exitMessage;
}
