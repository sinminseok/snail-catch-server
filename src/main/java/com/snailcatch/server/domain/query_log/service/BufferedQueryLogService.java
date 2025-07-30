package com.snailcatch.server.domain.query_log.service;

import com.snailcatch.server.domain.query_log.dto.QueryLogRequest;
import com.snailcatch.server.domain.query_log.entity.QueryLog;
import com.snailcatch.server.exception.custom.QueryLogDropException;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

@Service
@RequiredArgsConstructor
public class BufferedQueryLogService {

    private final QueryLogRetryService queryLogRetryService;

    private final BlockingQueue<QueryLog> buffer = new LinkedBlockingQueue<>(9_000_000);
    private static final int BATCH_SIZE = 5000;
    private static final int CONSUMER_COUNT = 36;

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
                QueryLog log = buffer.take();
                batch.add(log);
                buffer.drainTo(batch, BATCH_SIZE - 1);
                if (!batch.isEmpty()) {
                    saveLogsAsync(new ArrayList<>(batch));
                    batch.clear();
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            } catch (Exception e) {
                e.printStackTrace();
                batch.clear();
            }
        }
    }

    public void saveBufferedBatch(String key, List<QueryLogRequest> requests) throws InterruptedException {
        for (QueryLogRequest request : requests) {
            QueryLog log = QueryLog.from(key, request);
            buffer.put(log);
        }
    }

    @Async("queryLogExecutor")
    public void saveLogsAsync(List<QueryLog> logs) {
        queryLogRetryService.retryInsert(logs);
    }
}
