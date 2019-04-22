package com.adeo.batchmonitoring.domain;

import lombok.Data;

import java.util.Date;

@Data
public class ExecutionInfo {
    private Long id;
    private String name;
    private Date start;
    private Date end;
    private String status;
    private String exitMessage;
}
