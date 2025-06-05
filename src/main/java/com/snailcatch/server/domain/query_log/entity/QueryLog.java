package com.snailcatch.server.domain.query_log.entity;

import com.snailcatch.server.domain.query_log.dto.QueryLogRequest;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;


@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "query_log")
public class QueryLog {

    @Id
    private ObjectId id;

    @Field("key")
    private String key;

    @Field("method_name")
    private String methodName;

    @Field("sql_query")
    private String sqlQuery;

    @Field("execution_plan")
    private String executionPlan;

    @CreatedDate
    @Field("created_at")
    private LocalDateTime createdAt;

    @Field("duration")
    private long duration;

    public static QueryLog from(String key, QueryLogRequest request) {
        return QueryLog.builder()
                .key(key)
                .methodName(request.getMethodName())
                .sqlQuery(request.getSqlQuery())
                .executionPlan(request.getExecutionPlan())
                .duration(request.getDuration())
                .createdAt(request.getCreatedAt())
                .build();
    }
}
