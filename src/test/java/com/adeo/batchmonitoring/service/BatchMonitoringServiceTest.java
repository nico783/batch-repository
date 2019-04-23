package com.adeo.batchmonitoring.service;

import com.adeo.batchmonitoring.domain.BatchMonitoring;
import com.adeo.batchmonitoring.domain.BatchMonitoringRow;
import com.adeo.batchmonitoring.domain.ExecutionInfo;
import com.adeo.batchmonitoring.domain.StatisticInfo;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class BatchMonitoringServiceTest {

    private BatchMonitoringService batchMonitoringService;

    @Before
    public void setUp() {
        batchMonitoringService = new BatchMonitoringService() {
            @Override
            protected List<? extends BatchMonitoringRow> findBatchMonitoringRows() {

                List<BatchMonitoringRow> batchMonitoringRows = new ArrayList<>();

                ExecutionInfo stepInfo1 = getStepInfo(1L, "step1", "SUCCESS");
                ExecutionInfo jobInfo = getJobInfo();
                StatisticInfo statisticInfo1 = getStatisticInfo(100);
                HashMap<String, Object> customInfo1 = new HashMap<>();
                customInfo1.put("fileName", "file-for-test-1");
                BatchMonitoringRow row1 = new BatchMonitoringRow(stepInfo1, jobInfo, statisticInfo1, customInfo1);
                batchMonitoringRows.add(row1);

                ExecutionInfo stepInfo2 = getStepInfo(2L, "step2", "FAIL");
                StatisticInfo statisticInfo2 = getStatisticInfo(15);
                HashMap<String, Object> customInfo2 = new HashMap<>();
                customInfo2.put("fileName", "file-for-test-2");
                BatchMonitoringRow row2 = new BatchMonitoringRow(stepInfo2, jobInfo, statisticInfo2, customInfo2);
                batchMonitoringRows.add(row2);

                return batchMonitoringRows;
            }

            @Override
            protected String context() {
                return "test";
            }

            private StatisticInfo getStatisticInfo(Integer readCount) {
                StatisticInfo statisticInfo = new StatisticInfo();
                statisticInfo.setReadCount(readCount);
                return statisticInfo;
            }

            private ExecutionInfo getJobInfo() {
                ExecutionInfo jobInfo = new ExecutionInfo();
                jobInfo.setId(10L);
                jobInfo.setName("job");
                jobInfo.setStatus("IN PROGRESS");
                jobInfo.setExitMessage("Test job exit message");
                return jobInfo;
            }

            private ExecutionInfo getStepInfo(Long id, String name, String status) {
                ExecutionInfo stepInfo = new ExecutionInfo();
                stepInfo.setId(id);
                stepInfo.setName(name);
                stepInfo.setStatus(status);
                stepInfo.setExitMessage("Test step exit message.");
                return stepInfo;
            }
        };
    }

    @Test
    public void shouldGetBatchMonitoring() {
        // arrange & act
        BatchMonitoring batchMonitoring = batchMonitoringService.getBatchMonitoring();

        // assert
        assertEquals("test", batchMonitoring.getContext());

        assertEquals(2, batchMonitoring.getBatchMonitoringRows().size());
        assertEquals(new Long(1L), batchMonitoring.getBatchMonitoringRows().get(0).getStepInfo().getId());
        assertEquals("step1", batchMonitoring.getBatchMonitoringRows().get(0).getStepInfo().getName());
        assertEquals("SUCCESS", batchMonitoring.getBatchMonitoringRows().get(0).getStepInfo().getStatus());
        assertEquals("Test step exit message.", batchMonitoring.getBatchMonitoringRows().get(0).getStepInfo().getExitMessage());
        assertEquals("IN PROGRESS", batchMonitoring.getBatchMonitoringRows().get(0).getJobInfo().getStatus());
        assertEquals("job", batchMonitoring.getBatchMonitoringRows().get(0).getJobInfo().getName());
        assertEquals(new Long(10L), batchMonitoring.getBatchMonitoringRows().get(0).getJobInfo().getId());
        assertEquals(new Integer(100), batchMonitoring.getBatchMonitoringRows().get(0).getStatisticInfo().getReadCount());
        assertEquals("file-for-test-1", batchMonitoring.getBatchMonitoringRows().get(0).getCustomInfo().get("fileName"));

        assertEquals(new Long(2L), batchMonitoring.getBatchMonitoringRows().get(1).getStepInfo().getId());
        assertEquals("step2", batchMonitoring.getBatchMonitoringRows().get(1).getStepInfo().getName());
        assertEquals("FAIL", batchMonitoring.getBatchMonitoringRows().get(1).getStepInfo().getStatus());
        assertEquals("Test step exit message.", batchMonitoring.getBatchMonitoringRows().get(1).getStepInfo().getExitMessage());
        assertEquals("IN PROGRESS", batchMonitoring.getBatchMonitoringRows().get(1).getJobInfo().getStatus());
        assertEquals("job", batchMonitoring.getBatchMonitoringRows().get(1).getJobInfo().getName());
        assertEquals(new Long(10L), batchMonitoring.getBatchMonitoringRows().get(1).getJobInfo().getId());
        assertEquals(new Integer(15), batchMonitoring.getBatchMonitoringRows().get(1).getStatisticInfo().getReadCount());
        assertEquals("file-for-test-2", batchMonitoring.getBatchMonitoringRows().get(1).getCustomInfo().get("fileName"));
    }

    @Test
    public void shouldGetBatchMonitoringFiltered() {
        // arrange & act
        BatchMonitoring batchMonitoring = batchMonitoringService.getBatchMonitoring(row -> "SUCCESS".equals(row.getStepInfo().getStatus()));

        // assert
        assertEquals("test", batchMonitoring.getContext());

        assertEquals(1, batchMonitoring.getBatchMonitoringRows().size());
        assertEquals(new Long(1L), batchMonitoring.getBatchMonitoringRows().get(0).getStepInfo().getId());
        assertEquals("step1", batchMonitoring.getBatchMonitoringRows().get(0).getStepInfo().getName());
        assertEquals("SUCCESS", batchMonitoring.getBatchMonitoringRows().get(0).getStepInfo().getStatus());
        assertEquals("Test step exit message.", batchMonitoring.getBatchMonitoringRows().get(0).getStepInfo().getExitMessage());
        assertEquals("IN PROGRESS", batchMonitoring.getBatchMonitoringRows().get(0).getJobInfo().getStatus());
        assertEquals("job", batchMonitoring.getBatchMonitoringRows().get(0).getJobInfo().getName());
        assertEquals(new Long(10L), batchMonitoring.getBatchMonitoringRows().get(0).getJobInfo().getId());
        assertEquals(new Integer(100), batchMonitoring.getBatchMonitoringRows().get(0).getStatisticInfo().getReadCount());
        assertEquals("file-for-test-1", batchMonitoring.getBatchMonitoringRows().get(0).getCustomInfo().get("fileName"));
    }
}
