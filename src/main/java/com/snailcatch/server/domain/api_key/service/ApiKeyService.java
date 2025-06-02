package com.snailcatch.server.domain.api_key.service;

import com.snailcatch.server.domain.api_key.entity.ApiKey;
import com.snailcatch.server.domain.api_key.repository.ApiKeyRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class ApiKeyService {

    private final ApiKeyRepository apiKeyRepository;

    public ApiKeyService(ApiKeyRepository apiKeyRepository) {
        this.apiKeyRepository = apiKeyRepository;
    }

    public String generateApiKey() {
        String key = UUID.randomUUID().toString().replace("-", "");
        ApiKey apiKey = ApiKey.from(key);
        apiKeyRepository.save(apiKey);
        return key;
    }

    public boolean isValid(String key) {
        return apiKeyRepository.existsByKeyAndRevokedFalse(key);
    }
}

