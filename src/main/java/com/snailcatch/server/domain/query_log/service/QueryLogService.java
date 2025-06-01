package com.snailcatch.server.domain.query_log.service;

import com.snailcatch.server.domain.query_log.QueryLog;
import com.snailcatch.server.domain.query_log.dto.QueryLogRequest;
import com.snailcatch.server.domain.query_log.repository.QueryLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QueryLogService {

    private final QueryLogRepository queryLogRepository;

    public void save(QueryLogRequest queryLogRequest){
        QueryLog queryLog = QueryLog.from(queryLogRequest);
        queryLogRepository.save(queryLog);
    }
}
