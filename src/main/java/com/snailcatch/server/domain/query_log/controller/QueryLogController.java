package com.snailcatch.server.domain.query_log.controller;

import com.snailcatch.server.domain.query_log.dto.QueryLogCursorResponse;
import com.snailcatch.server.domain.query_log.dto.QueryLogRequest;
import com.snailcatch.server.domain.query_log.service.BufferedQueryLogService;
import com.snailcatch.server.domain.query_log.service.QueryLogService;
import com.snailcatch.server.exception.custom.QueryLogDropException;
import com.snailcatch.server.global.dto.SuccessResponse;
import com.snailcatch.server.global.annotation.ApiKey;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/query-logs")
public class QueryLogController {

    private final QueryLogService queryLogService;
    private final BufferedQueryLogService bufferedQueryLogService;

    @PostMapping
    public ResponseEntity<?> saveLogs(@RequestBody final List<QueryLogRequest> queryLogRequest, @ApiKey String apiKey) throws InterruptedException {
        bufferedQueryLogService.saveBufferedBatch(apiKey, queryLogRequest);
        SuccessResponse response = new SuccessResponse(true, "쿼리 로그 저장 성공", null);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @GetMapping("/cursor")
    public ResponseEntity<?> findByCursor(
            @RequestParam(required = false) String cursorCreatedAt,
            @RequestParam(defaultValue = "15") int size,
            @ApiKey String apiKey
    ) {
        QueryLogCursorResponse byCursor = queryLogService.findByCursor(apiKey, cursorCreatedAt, size);
        SuccessResponse response = new SuccessResponse(true, "커서 기반 쿼리 로그 조회", byCursor);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{logId}")
    public ResponseEntity<?> deleteById(@PathVariable String logId, @ApiKey String apiKey) {
        queryLogService.delete(new ObjectId(logId));
        SuccessResponse response = new SuccessResponse(true, "쿼리 삭제 성공", null);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
