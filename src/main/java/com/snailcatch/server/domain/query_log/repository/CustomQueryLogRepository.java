package com.snailcatch.server.domain.query_log.repository;

import com.snailcatch.server.domain.query_log.dto.QueryLogResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CustomQueryLogRepository {
    List<QueryLogResponse> findLogsByPageable(String key, Pageable pageable);
}
