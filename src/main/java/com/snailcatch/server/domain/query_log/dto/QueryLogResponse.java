package com.snailcatch.server.domain.query_log.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class QueryLogResponse {

    private String methodName;

    private String sqlQuery;

    private String executionPlan;

    private long duration;

    private LocalDateTime createdAt;
}
