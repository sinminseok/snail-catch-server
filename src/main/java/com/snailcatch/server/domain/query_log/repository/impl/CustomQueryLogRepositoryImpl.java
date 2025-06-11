package com.snailcatch.server.domain.query_log.repository.impl;

import com.snailcatch.server.domain.query_log.dto.QueryLogResponse;
import com.snailcatch.server.domain.query_log.entity.QueryLog;
import com.snailcatch.server.domain.query_log.repository.CustomQueryLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    public Page<QueryLogResponse> findLogsByPageable(String key, Pageable pageable) {
        Query query = new Query()
                .addCriteria(Criteria.where("key").is(key))
                .with(pageable)
                .with(Sort.by(Sort.Direction.DESC, "created_at"));

        List<QueryLog> logs = mongoTemplate.find(query, QueryLog.class);

        long total = mongoTemplate.count(Query.of(query).limit(-1).skip(-1), QueryLog.class); // 전체 카운트

        List<QueryLogResponse> content = logs.stream()
                .map(log -> QueryLogResponse.builder()
                        .id(log.getId().toHexString())
                        .methodName(log.getMethodName())
                        .sqlQuery(log.getSqlQuery())
                        .executionPlan(log.getExecutionPlan())
                        .duration(log.getDuration())
                        .createdAt(log.getCreatedAt())
                        .build())
                .toList();

        return new PageImpl<>(content, pageable, total); // Page 객체로 래핑
    }

}
