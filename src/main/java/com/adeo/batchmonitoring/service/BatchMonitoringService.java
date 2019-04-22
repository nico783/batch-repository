package com.adeo.batchmonitoring.service;

import com.adeo.batchmonitoring.domain.BatchMonitoring;
import com.adeo.batchmonitoring.domain.BatchMonitoringRow;

import java.util.ArrayList;
import java.util.List;

public abstract class BatchMonitoringService {

    public final BatchMonitoring getBatchMonitoring(){
        BatchMonitoring batchMonitoring = new BatchMonitoring();
        batchMonitoring.setRows(new ArrayList<>(findBatchMonitoringRows()));
        batchMonitoring.setContext(context());
        return batchMonitoring;
    }

    protected abstract List<? extends BatchMonitoringRow> findBatchMonitoringRows();

    protected abstract String context();
}
