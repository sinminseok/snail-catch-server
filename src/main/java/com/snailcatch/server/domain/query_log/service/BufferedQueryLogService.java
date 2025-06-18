package com.snailcatch.server.domain.query_log.service;

import com.snailcatch.server.domain.query_log.dto.QueryLogRequest;
import com.snailcatch.server.domain.query_log.entity.QueryLog;
import com.snailcatch.server.exception.custom.QueryLogDropException;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

@Service
@RequiredArgsConstructor
public class BufferedQueryLogService {
    @Autowired
    private MongoTemplate mongoTemplate;

    private final BlockingQueue<QueryLog> buffer = new LinkedBlockingQueue<>(9_000_000);
    private static final int BATCH_SIZE = 5000;
    private static final int CONSUMER_COUNT = 36; // 병렬 Consumer 수

    @PostConstruct
    public void init() {
        ExecutorService executor = Executors.newFixedThreadPool(CONSUMER_COUNT);
        for (int i = 0; i < CONSUMER_COUNT; i++) {
            executor.submit(this::consumeLogs);
        }
    }

    private void consumeLogs() {
        List<QueryLog> batch = new ArrayList<>(BATCH_SIZE);
        while (true) {
            try {
                QueryLog log = buffer.take(); // 대기 (blocking) cpu 가 안 돌면서 대기함
                batch.add(log);
                buffer.drainTo(batch, BATCH_SIZE - 1); // take 한 거 외에 최대 BATCH_SIZE-1개 추가

                if (!batch.isEmpty()) {
                    saveLogsAsync(new ArrayList<>(batch)); // 복사본 전달
                    batch.clear(); // 재사용
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            } catch (Exception e) {
                e.printStackTrace();
                batch.clear(); // 에러 시 버리고 다시 시작
            }
        }
    }

    public void saveBufferedBatch(String key, List<QueryLogRequest> requests) {
        for (QueryLogRequest request : requests) {
            QueryLog log = QueryLog.from(key, request);
            boolean added = buffer.offer(log);
            if (!added) {
                throw new QueryLogDropException();
                //todo 버려진 로그에 대한 처리
            }
        }
    }

    @Async("queryLogExecutor")
    public void saveLogsAsync(List<QueryLog> logs) {
        try {
            mongoTemplate.insert(logs, QueryLog.class);
        } catch (Exception e) {
            System.err.println("Error saving logs: " + e.getMessage());
            e.printStackTrace();
        }
    }
}