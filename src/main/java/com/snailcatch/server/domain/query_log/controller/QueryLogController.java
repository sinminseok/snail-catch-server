package com.snailcatch.server.domain.query_log.controller;

import com.snailcatch.server.domain.query_log.dto.QueryLogRequest;
import com.snailcatch.server.domain.query_log.dto.QueryLogResponse;
import com.snailcatch.server.domain.query_log.service.BufferedQueryLogService;
import com.snailcatch.server.domain.query_log.service.QueryLogService;
import com.snailcatch.server.global.dto.SuccessResponse;
import com.snailcatch.server.global.annotation.ApiKey;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    public ResponseEntity<?> saveLogs(@RequestBody final List<QueryLogRequest> queryLogRequest, @ApiKey String apiKey) {
        bufferedQueryLogService.saveBufferedBatch(apiKey, queryLogRequest);
        SuccessResponse response = new SuccessResponse(true, "success save query logs", null);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<?> findByPage(
            @ApiKey String apiKey,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    )  {
        Pageable pageable = PageRequest.of(page, size);
        List<QueryLogResponse> queryLogResponses = queryLogService.findByPage(apiKey, pageable);
        SuccessResponse response = new SuccessResponse(true, "쿼리 페이징 조회", queryLogResponses);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
