package com.snailcatch.server.domain.api_key.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "`api_key`")
public class ApiKey {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "api_key_id", nullable = false)
    private Long id;

    @Column(name = "`key`", unique = true, nullable = false)
    private String key;

    @Column(name = "`created_at`", nullable = false)
    private LocalDateTime createdAt;

    @Setter
    @Column(name = "`revoked`", nullable = false)
    private boolean revoked;

    public static ApiKey from(String key) {
        return ApiKey.builder()
                .key(key)
                .createdAt(LocalDateTime.now())
                .revoked(false)
                .build();
    }
}

