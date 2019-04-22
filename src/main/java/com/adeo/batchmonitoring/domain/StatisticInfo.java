package com.adeo.batchmonitoring.domain;

import lombok.Data;

@Data
public class StatisticInfo {
    private Integer readCount;
    private Integer writeCount;
    private Integer commitCount;
}
