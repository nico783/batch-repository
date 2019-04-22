package com.adeo.batchmonitoring.domain;

import lombok.Data;

import java.util.List;

@Data
public class BatchMonitoring {
    private List<BatchMonitoringRow> rows;
    private String context;
}
