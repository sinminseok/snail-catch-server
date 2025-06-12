package com.snailcatch.server.domain.api_key.repository;

import com.snailcatch.server.domain.api_key.entity.ApiKey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ApiKeyRepository extends JpaRepository<ApiKey, Long> {
    boolean existsByKeyAndRevokedFalse(String key);
}
