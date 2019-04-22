package com.adeo.batchmonitoring.repository;

import com.adeo.batchmonitoring.domain.BatchMonitoringRow;
import com.adeo.batchmonitoring.domain.ExecutionInfo;
import com.adeo.batchmonitoring.domain.StatisticInfo;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * Repository "implémentant" une stratégie de monitoring de batchs définis avec spring batch
 * (avec les tables standards de monitoring enregistrées en bdd), sans nécessité de récupération d'informations
 * non standards. Son utilisation nécessite de spécifier une implémentation de ce repository dans l'application cliente.
 *
 * @author Nicolas Benizri
 */
public abstract class SimpleSpringBatchMonitoringRepository {

    /**
     * Récupération des informations de monitoring standard depuis les tables de monitoring Spring Batch.
     *
     * @return monitoring de spring batch.
     */
    public List<? extends BatchMonitoringRow> getMonitoringRows() {
        return getJdbcTemplate().query(
                "select bji.JOB_INSTANCE_ID , bji.JOB_NAME, " +
                        " bje.JOB_EXECUTION_ID, bje.START_TIME as JOB_START ,bje.END_TIME as JOB_END, bje.STATUS as JOB_STATUS, bje.EXIT_CODE as JOB_EXIT_CODE, bje.EXIT_MESSAGE as JOB_EXIT_MESSAGE, " +
                        " bse.STEP_EXECUTION_ID, bse.STEP_NAME, bse.START_TIME as STEP_START, bse.END_TIME as STEP_END, bse.STATUS as STEP_STATUS, bse.COMMIT_COUNT, bse.READ_COUNT, bse.WRITE_COUNT, bse.EXIT_CODE as STEP_EXIT_CODE, bse.EXIT_MESSAGE as STEP_EXIT_MESSAGE " +
                        " from BATCH_STEP_EXECUTION bse " +
                        " join BATCH_JOB_EXECUTION bje on bse.JOB_EXECUTION_ID = bje.JOB_EXECUTION_ID " +
                        " join BATCH_JOB_INSTANCE bji on bje.JOB_INSTANCE_ID = bji.JOB_INSTANCE_ID ",
                getMapper()
        );
    }

    private RowMapper<? extends BatchMonitoringRow> getMapper() {
        return (resultSet, i) -> new BatchMonitoringRow(
                getStepInfo(resultSet),
                getJobInfo(resultSet),
                getStatistiqueInfo(resultSet),
                getCustomInfo(resultSet));
    }

    /**
     * Permet de définir des informations personnalisé.
     *
     * @param resultSet ensemble des résultats de la requete définie dans {@link SimpleSpringBatchMonitoringRepository#getMonitoringRows()}
     * @return informations personnalisées sous la forme d'une map.
     */
    protected Map<String, Object> getCustomInfo(ResultSet resultSet) {
        return null;
    }

    /**
     * Permet de définir des informations liées à l'execution d'un job.
     *
     * @param resultSet ensemble des résultats de la requete définie dans {@link SimpleSpringBatchMonitoringRepository#getMonitoringRows()}
     * @return informations du job.
     */
    protected ExecutionInfo getJobInfo(ResultSet resultSet) throws SQLException {
        ExecutionInfo jobInfo = new ExecutionInfo();
        jobInfo.setName(resultSet.getString("JOB_NAME"));
        return jobInfo;
    }

    /**
     * Permet de définir des informations liées à l'execution d'un step.
     *
     * @param resultSet ensemble des résultats de la requete définie dans {@link SimpleSpringBatchMonitoringRepository#getMonitoringRows()}
     * @return informations du step.
     */
    protected ExecutionInfo getStepInfo(ResultSet resultSet) throws SQLException {
        ExecutionInfo stepInfo = new ExecutionInfo();
        stepInfo.setName(resultSet.getString("STEP_NAME"));
        return stepInfo;
    }

    /**
     * Permet de définir des statisques liées à l'execution.
     *
     * @param resultSet ensemble des résultats de la requete définie dans {@link SimpleSpringBatchMonitoringRepository#getMonitoringRows()}
     * @return statistiques de l'execution.
     */
    protected StatisticInfo getStatistiqueInfo(ResultSet resultSet) throws SQLException {
        StatisticInfo statisticInfo = new StatisticInfo();
        statisticInfo.setReadCount(resultSet.getInt("READ_COUNT"));
        return statisticInfo;
    }

    /**
     * Méthode a implémenter dans le but de définir le jdbcTemplate devant être utilisé.
     *
     * @return jdbcTemplate de reference de l'application cliente.
     */
    protected abstract JdbcTemplate getJdbcTemplate();

}
