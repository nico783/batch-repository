package com.adeo.batchmonitoring.service;

import com.adeo.batchmonitoring.domain.BatchMonitoringInfo;
import com.adeo.batchmonitoring.domain.StepInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public abstract class BatchMonitoringService {

    public final BatchMonitoringInfo getMonitoring(){
        BatchMonitoringInfo batchMonitoringInfo = new BatchMonitoringInfo();
        batchMonitoringInfo.setStepInfos(new ArrayList<>(findSteps()));
        batchMonitoringInfo.setContext(context());
        return batchMonitoringInfo;
    }

    protected abstract List<? extends StepInfo> findSteps();

    protected abstract String context();

}
