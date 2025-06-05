package com.snailcatch.server.domain.api_key.controller;

import com.snailcatch.server.domain.api_key.service.ApiKeyService;
import com.snailcatch.server.utils.SuccessResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/key")
@RequiredArgsConstructor
public class ApiKeyController {

    private final ApiKeyService apiKeyService;

    @PostMapping("/generate")
    public ResponseEntity<SuccessResponse> generateKey() {
        String key = apiKeyService.generateApiKey();
        SuccessResponse response = new SuccessResponse(true, "Rest API 발급", key);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
