package com.snailcatch.server.domain.query_log.dto;

import java.time.LocalDateTime;
import java.util.List;

public record QueryLogCursorResponse(
        List<QueryLogResponse> logs,
        boolean hasNext,
        LocalDateTime nextCursorCreatedAt
) {}