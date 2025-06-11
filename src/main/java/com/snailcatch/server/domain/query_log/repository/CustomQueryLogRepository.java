package com.snailcatch.server.domain.query_log.repository;

import com.snailcatch.server.domain.query_log.dto.QueryLogResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomQueryLogRepository {
    Page<QueryLogResponse> findLogsByPageable(String key, Pageable pageable);
}
