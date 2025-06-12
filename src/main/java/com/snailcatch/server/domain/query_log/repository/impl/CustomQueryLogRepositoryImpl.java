package com.snailcatch.server.domain.query_log.repository.impl;

import com.snailcatch.server.domain.query_log.dto.QueryLogCursorResponse;
import com.snailcatch.server.domain.query_log.dto.QueryLogResponse;
import com.snailcatch.server.domain.query_log.entity.QueryLog;
import com.snailcatch.server.domain.query_log.repository.CustomQueryLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class CustomQueryLogRepositoryImpl implements CustomQueryLogRepository {

    private final MongoTemplate mongoTemplate;

    @Override
    public QueryLogCursorResponse findLogsByCursor(String key, LocalDateTime cursorCreatedAt, int size) {
        Criteria criteria = Criteria.where("key").is(key);

        if (cursorCreatedAt != null) {
            Date cursorCreatedAtDate = Date.from(cursorCreatedAt.toInstant(ZoneOffset.UTC));
            criteria = criteria.and("created_at").lt(cursorCreatedAtDate);
        }

        Query query = new Query()
                .addCriteria(criteria)
                .with(Sort.by(Sort.Direction.DESC, "created_at"))
                .limit(size + 1);

        List<QueryLog> logs = mongoTemplate.find(query, QueryLog.class);
        boolean hasNext = logs.size() > size;
        if (hasNext) {
            logs.remove(size);
        }

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

        LocalDateTime nextCursor = hasNext
                ? logs.get(logs.size() - 1).getCreatedAt()
                : null;

        return new QueryLogCursorResponse(content, hasNext, nextCursor);
    }

}
