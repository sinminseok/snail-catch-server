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
        Query query = buildQuery(key, cursorCreatedAt, size + 1);
        List<QueryLog> logs = mongoTemplate.find(query, QueryLog.class);
        boolean hasNext = logs.size() > size;
        if (hasNext) {
            logs.remove(size); // 페이징 커서 처리용 마지막 항목 제거
        }
        List<QueryLogResponse> content = convertToResponseList(logs);
        LocalDateTime nextCursor = extractNextCursor(logs, hasNext);
        return new QueryLogCursorResponse(content, hasNext, nextCursor);
    }

    private Query buildQuery(String key, LocalDateTime cursorCreatedAt, int limit) {
        Criteria criteria = Criteria.where("key").is(key);

        if (cursorCreatedAt != null) {
            Date cursorDate = Date.from(cursorCreatedAt.toInstant(ZoneOffset.UTC));
            criteria = criteria.and("created_at").lt(cursorDate);
        }

        return new Query()
                .addCriteria(criteria)
                .with(Sort.by(Sort.Direction.DESC, "created_at"))
                .limit(limit);
    }

    private List<QueryLogResponse> convertToResponseList(List<QueryLog> logs) {
        return logs.stream()
                .map(log -> QueryLogResponse.toQueryLogResponse(log))
                .toList();
    }

    private LocalDateTime extractNextCursor(List<QueryLog> logs, boolean hasNext) {
        if (!hasNext || logs.isEmpty()) {
            return null;
        }
        return logs.get(logs.size() - 1).getCreatedAt();
    }

}
