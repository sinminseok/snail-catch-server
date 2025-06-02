package com.snailcatch.server.domain.query_log.controller;

import com.snailcatch.server.domain.query_log.dto.QueryLogRequest;
import com.snailcatch.server.domain.query_log.service.QueryLogService;
import com.snailcatch.server.utils.SuccessResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/query-logs")
public class QueryLogController {

    private final QueryLogService queryLogService;

    @PostMapping("")
    public ResponseEntity<?> sendCheckCode(@RequestBody final QueryLogRequest queryLogRequest) {
        SuccessResponse response = new SuccessResponse(true, "success save query logs", null);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<?> findByPage() {
        SuccessResponse response = new SuccessResponse(true, "success save query logs", null);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
