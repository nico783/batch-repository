package com.adeo.batchmonitoring.domain;

import lombok.Data;

import java.util.Date;
import java.util.Map;

@Data
public class StepInfo {
    private Long id;
    private JobInfo jobInfo;
    private String name;
    private Date start;
    private Date end;
    private String status;
    private Integer readCount;
    private Integer writeCount;
    private Integer commitCount;
    private String exitMessage;
    private Map<String, Object> customInfo;
}
