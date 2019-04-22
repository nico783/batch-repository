package com.adeo.batchmonitoring.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;
import java.util.Objects;

@Data
@AllArgsConstructor
public class BatchMonitoringRow {
    private ExecutionInfo stepInfo;
    private ExecutionInfo jobInfo;
    private StatisticInfo statisticInfo;
    private Map<String, Object> customInfo;

    public MonitoringType getMonitoringType(){
        if(Objects.isNull(stepInfo)){
            return MonitoringType.JOB;
        } else {
            return MonitoringType.STEP;
        }
    }
}
