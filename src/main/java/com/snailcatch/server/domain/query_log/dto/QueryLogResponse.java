package com.snailcatch.server.domain.query_log.dto;

import com.snailcatch.server.domain.query_log.entity.QueryLog;
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

    private String id;

    private String methodName;

    private String sqlQuery;

    private String executionPlan;

    private long duration;

    private LocalDateTime createdAt;

    public static QueryLogResponse toQueryLogResponse(final QueryLog log) {
        return QueryLogResponse.builder()
                .id(log.getId().toHexString())
                .methodName(log.getMethodName())
                .sqlQuery(log.getSqlQuery())
                .executionPlan(log.getExecutionPlan())
                .duration(log.getDuration())
                .createdAt(log.getCreatedAt())
                .build();
    }
}
