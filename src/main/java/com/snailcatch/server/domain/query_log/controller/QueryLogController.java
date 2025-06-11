package com.snailcatch.server.domain.query_log.controller;

import com.snailcatch.server.domain.query_log.dto.QueryLogRequest;
import com.snailcatch.server.domain.query_log.dto.QueryLogResponse;
import com.snailcatch.server.domain.query_log.service.BufferedQueryLogService;
import com.snailcatch.server.domain.query_log.service.QueryLogService;
import com.snailcatch.server.global.dto.PaginationResponse;
import com.snailcatch.server.global.dto.SuccessResponse;
import com.snailcatch.server.global.annotation.ApiKey;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
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



    @GetMapping
    public ResponseEntity<?> findByPage(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "15") int size, @ApiKey String apiKey) {
        queryLogService.testCount();
        Pageable pageable = PageRequest.of(page, size);
        Page<QueryLogResponse> result = queryLogService.findByPage(apiKey, pageable);
        PaginationResponse<QueryLogResponse> paginationResponse = PaginationResponse.of(result);
        SuccessResponse response = new SuccessResponse(true, "쿼리 페이징 조회", paginationResponse);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{logId}")
    public ResponseEntity<?> deleteById(@PathVariable String logId, @ApiKey String apiKey) {
        queryLogService.delete(new ObjectId(logId));
        SuccessResponse response = new SuccessResponse(true, "쿼리 삭제 성공", null);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
