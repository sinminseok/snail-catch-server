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
    @Setter
    private String key;

    @Setter
    private LocalDateTime createdAt;

    private boolean revoked;

    public static ApiKey from(String key) {
        return ApiKey.builder()
                .key(key)
                .createdAt(LocalDateTime.now())
                .revoked(false)
                .build();
    }
}

