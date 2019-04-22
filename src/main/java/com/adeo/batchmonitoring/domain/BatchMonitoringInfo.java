package com.adeo.batchmonitoring.domain;

import lombok.Data;

import java.util.List;

@Data
public class BatchMonitoringInfo {
    private List<StepInfo> stepInfos;
    private String context;
}
