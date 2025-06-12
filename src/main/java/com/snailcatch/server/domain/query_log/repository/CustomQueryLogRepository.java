package com.snailcatch.server.domain.query_log.repository;

import com.snailcatch.server.domain.query_log.dto.QueryLogCursorResponse;

import java.time.LocalDateTime;

public interface CustomQueryLogRepository {
    QueryLogCursorResponse findLogsByCursor(String key, LocalDateTime cursorCreatedAt, int size);
}
