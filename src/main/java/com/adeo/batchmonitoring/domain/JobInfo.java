package com.adeo.batchmonitoring.domain;

import lombok.Data;

import java.util.Date;
import java.util.Map;

@Data
public class JobInfo {
    private Long id;
    private String name;
    private Date start;
    private Date end;
    private String status;
    private String exitMessage;
    private Map<String, Object> customInfo;
}
