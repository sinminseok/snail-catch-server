package com.snailcatch.server.domain.query_log.dto;

import java.time.LocalDateTime;

public record QueryLogCursorRequest(String key, LocalDateTime cursorCreatedAt, int size) {}

