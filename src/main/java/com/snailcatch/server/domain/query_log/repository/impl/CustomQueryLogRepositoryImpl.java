package com.snailcatch.server.domain.query_log.repository.impl;

import com.snailcatch.server.domain.query_log.dto.QueryLogResponse;
import com.snailcatch.server.domain.query_log.entity.QueryLog;
import com.snailcatch.server.domain.query_log.repository.CustomQueryLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CustomQueryLogRepositoryImpl implements CustomQueryLogRepository {

    private final MongoTemplate mongoTemplate;

    @Override
    public List<QueryLogResponse> findLogsByPageable(String key, Pageable pageable) {
        Query query = new Query()
                .addCriteria(Criteria.where("key").is(key))
                .with(pageable)
                .with(org.springframework.data.domain.Sort.by(org.springframework.data.domain.Sort.Direction.DESC, "created_at"));
        List<QueryLog> logs = mongoTemplate.find(query, QueryLog.class);
        return logs.stream()
                .map(log -> QueryLogResponse.builder()
                        .methodName(log.getMethodName())
                        .sqlQuery(log.getSqlQuery())
                        .executionPlan(log.getExecutionPlan())
                        .duration(log.getDuration())
                        .createdAt(log.getCreatedAt())
                        .build())
                .toList();
    }
}
