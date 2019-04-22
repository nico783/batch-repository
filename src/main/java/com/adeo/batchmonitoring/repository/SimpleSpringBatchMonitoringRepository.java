package com.adeo.batchmonitoring.repository;

import com.adeo.batchmonitoring.domain.JobInfo;
import com.adeo.batchmonitoring.domain.StepInfo;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.util.List;

public abstract class SimpleSpringBatchMonitoringRepository {

    public List<? extends StepInfo> findSteps(){
        List<? extends StepInfo> query = getJdbcTemplate().query(
                "select bji.JOB_INSTANCE_ID , bji.JOB_NAME, " +
                " bje.JOB_EXECUTION_ID, bje.START_TIME as JOB_START ,bje.END_TIME as JOB_END, bje.STATUS as JOB_STATUS, bje.EXIT_CODE as JOB_EXIT_CODE, bje.EXIT_MESSAGE as JOB_EXIT_MESSAGE, " +
                " bse.STEP_EXECUTION_ID, bse.STEP_NAME, bse.START_TIME as STEP_START, bse.END_TIME as STEP_END, bse.STATUS as STEP_STATUS, bse.COMMIT_COUNT, bse.READ_COUNT, bse.WRITE_COUNT, bse.EXIT_CODE as STEP_EXIT_CODE, bse.EXIT_MESSAGE as STEP_EXIT_MESSAGE " +
                " from BATCH_STEP_EXECUTION bse " +
                " join BATCH_JOB_EXECUTION bje on bse.JOB_EXECUTION_ID = bje.JOB_EXECUTION_ID " +
                " join BATCH_JOB_INSTANCE bji on bje.JOB_INSTANCE_ID = bji.JOB_INSTANCE_ID ",
                getMapper()
        );
        return query;
    }

    private RowMapper<? extends StepInfo> getMapper(){
        return (resultSet, i) -> {

            JobInfo jobInfo = new JobInfo();
            jobInfo.setName(resultSet.getString("JOB_NAME"));

            StepInfo stepInfo = new StepInfo();
            stepInfo.setName(resultSet.getString("STEP_NAME"));
            stepInfo.setJobInfo(jobInfo);

            return stepInfo;
        };
    }

    protected abstract JdbcTemplate getJdbcTemplate();

}
