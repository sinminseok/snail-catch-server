

package com.snailcatch.server.domain.query_log.service;

import com.snailcatch.server.domain.query_log.dto.QueryLogRequest;
import com.snailcatch.server.domain.query_log.entity.QueryLog;
import com.snailcatch.server.domain.query_log.repository.QueryLogRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

@Service
@RequiredArgsConstructor
public class BufferedQueryLogService {
    @Autowired
    private MongoTemplate mongoTemplate;
    private final BlockingQueue<QueryLog> buffer = new LinkedBlockingQueue<>(10000); // 최대 1만건 버퍼

    @PostConstruct
    public void init() {
        Executors.newSingleThreadExecutor().execute(() -> {
            while (true) {
                try {
                    flushBuffer();           // 버퍼 비움
                    Thread.sleep(1000);      // 1초 대기
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt(); // 현재 쓰레드 인터럽트 설정
                    break; // 루프 종료
                } catch (Exception e) {
                    // flushBuffer에서 발생하는 예외는 로깅하고 루프 계속
                    e.printStackTrace();
                }
            }
        });
    }

    // 다건 로그 요청 처리
    public void saveBufferedBatch(String key, List<QueryLogRequest> requests) {
        for (QueryLogRequest request : requests) {
            QueryLog log = QueryLog.from(key, request);
            buffer.offer(log); // 큐에 하나씩 적재
        }
    }

    // 큐에서 꺼내서 DB에 저장 요청 (비동기 메서드 호출)
    private void flushBuffer() {
        List<QueryLog> batch = new ArrayList<>();
        buffer.drainTo(batch, 1000); // 최대 1000건까지 비움
        if (!batch.isEmpty()) {
            saveLogsAsync(batch);
        }
    }

    // 실제 DB 저장은 비동기로 처리
    @Async
    public void saveLogsAsync(List<QueryLog> logs) {
        try {
            mongoTemplate.insert(logs, QueryLog.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}





