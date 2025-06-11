package com.snailcatch.server.domain.query_log.service;

import com.snailcatch.server.domain.query_log.dto.QueryLogResponse;
import com.snailcatch.server.domain.query_log.repository.QueryLogRepository;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class QueryLogService {

    private final QueryLogRepository queryLogRepository;

    public Page<QueryLogResponse> findByPage(final String key, final Pageable pageable) {
        return queryLogRepository.findLogsByPageable(key, pageable);
    }

    public void testCount(){
        System.out.println("COUNTTT =" + queryLogRepository.count());
    }

    public void delete(final ObjectId logId) {
        queryLogRepository.deleteById(logId);
    }
}
