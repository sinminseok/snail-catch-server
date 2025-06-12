package com.snailcatch.server.domain.query_log.service;

import com.snailcatch.server.domain.query_log.dto.QueryLogCursorResponse;
import com.snailcatch.server.domain.query_log.repository.QueryLogRepository;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


@Service
@RequiredArgsConstructor
public class QueryLogService {

    private final QueryLogRepository queryLogRepository;

    public QueryLogCursorResponse findByCursor(String key, String cursorCreatedAt, int size) {
        LocalDateTime cursorTime = null;
        if (cursorCreatedAt != null && !cursorCreatedAt.isBlank()) {
            cursorTime = LocalDateTime.parse(cursorCreatedAt, DateTimeFormatter.ISO_DATE_TIME);
        }
        return queryLogRepository.findLogsByCursor(key, cursorTime, size);
    }

    public void delete(final ObjectId logId) {
        queryLogRepository.deleteById(logId);
    }
}
