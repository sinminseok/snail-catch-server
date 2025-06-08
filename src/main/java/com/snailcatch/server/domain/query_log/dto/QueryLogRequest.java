package com.snailcatch.server.domain.query_log.dto;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Getter
public class QueryLogRequest {

    private String methodName;

    private List<String> sqlQueries;

    private List<List<Map<String, String>>> executionPlans;

    private long duration;

    private LocalDateTime createdAt;


}
