package com.snailcatch.server.domain.query_log.service;

import com.snailcatch.server.domain.query_log.entity.QueryLog;
import com.snailcatch.server.exception.custom.QueryLogSaveException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * retryInsert() 호출 → 예외 발생 → 재시도 (3회) 실패 → recover() 메서드 자동 호출
 */
@Service
@RequiredArgsConstructor
public class QueryLogRetryService {

    private final RedisTemplate<String, Object> redisTemplate;
    private static final String QUEUE_KEY = "failed:queryLogs";
    private final MongoTemplate mongoTemplate;

    private static final int MAX_RETRY = 3;

    @Retryable(value = Exception.class, maxAttempts = MAX_RETRY, backoff = @Backoff(delay = 3000))
    public void retryInsert(List<QueryLog> logs) {
        mongoTemplate.insert(logs, QueryLog.class);
    }

    @Recover
    public void recover(Exception e, List<QueryLog> logs) {
        // 재시도 실패 시 뭔가 처리하긴 해야될듯..
        // 예시로 커스텀 예외 던지기
        throw new QueryLogSaveException();
    }
}
