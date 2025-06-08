package com.snailcatch.server.domain.query_log.entity;

import com.snailcatch.server.domain.query_log.dto.QueryLogRequest;
import com.snailcatch.server.global.formatter.LogFormatter;
import com.snailcatch.server.global.formatter.SqlFormatter;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


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
        String formattedSql = SqlFormatter.formatSqls(request.getSqlQueries());
        String formattedPlan = formattedPlan(request.getExecutionPlans());
        return QueryLog.builder()
                .key(key)
                .methodName(request.getMethodName())
                .sqlQuery(formattedSql)
                .executionPlan(formattedPlan)
                .duration(request.getDuration())
                .createdAt(request.getCreatedAt())
                .build();
    }

    private static String formattedPlan(List<List<Map<String, String>>> executionPlans) {
        return executionPlans.stream()
                .map(LogFormatter::formatExplain)
                .collect(Collectors.joining("\n\n"));
    }
}
