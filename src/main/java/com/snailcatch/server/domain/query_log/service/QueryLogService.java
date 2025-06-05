package com.snailcatch.server.domain.query_log.service;

import com.snailcatch.server.domain.query_log.dto.QueryLogResponse;
import com.snailcatch.server.domain.query_log.entity.QueryLog;
import com.snailcatch.server.domain.query_log.dto.QueryLogRequest;
import com.snailcatch.server.domain.query_log.repository.QueryLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class QueryLogService {

    private final QueryLogRepository queryLogRepository;

    public void save(String key, QueryLogRequest queryLogRequest){
        QueryLog queryLog = QueryLog.from(key, queryLogRequest);
        queryLogRepository.save(queryLog);
    }

    public void saveAll(String key, List<QueryLogRequest> logs) {
        List<QueryLog> queryLogs = new ArrayList<>();
        for(QueryLogRequest log : logs){
            queryLogs.add(QueryLog.from(key, log));
        }
        queryLogRepository.saveAll(queryLogs);
    }

    public List<QueryLogResponse> findByPage(String key, Pageable pageable){
        return queryLogRepository.findLogsByPageable(key, pageable);
    }
}
