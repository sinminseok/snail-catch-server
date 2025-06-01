package com.snailcatch.server.domain.query_log.dto;

import lombok.Getter;

@Getter
public class QueryLogRequest {

    private String query;

    private String methodName;

    private String sqlQuery;

    private String executionPlan;

    private long duration;
}
