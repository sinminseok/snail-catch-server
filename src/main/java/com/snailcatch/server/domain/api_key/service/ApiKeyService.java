package com.snailcatch.server.domain.api_key.service;

import com.snailcatch.server.domain.api_key.entity.ApiKey;
import com.snailcatch.server.domain.api_key.repository.ApiKeyRepository;
import com.snailcatch.server.global.utils.ApiKeyGenerator;
import org.springframework.stereotype.Service;

@Service
public class ApiKeyService {

    private final ApiKeyRepository apiKeyRepository;
    private final ApiKeyGenerator apiKeyGenerator;

    public ApiKeyService(ApiKeyRepository apiKeyRepository, ApiKeyGenerator apiKeyGenerator) {
        this.apiKeyRepository = apiKeyRepository;
        this.apiKeyGenerator = apiKeyGenerator;
    }

    public String generateApiKey() {
        String key = apiKeyGenerator.generate();
        ApiKey apiKey = ApiKey.from(key);
        apiKeyRepository.save(apiKey);
        return key;
    }

    public boolean isValid(String key) {
        return apiKeyRepository.existsByKeyAndRevokedFalse(key);
    }
}
