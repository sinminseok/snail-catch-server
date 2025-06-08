package com.snailcatch.server.auth;


import java.util.List;

public final class ApiPaths {

    private ApiPaths() {} // 인스턴스화 방지

    // 공개 허용되는 API 엔드포인트
    public static final String[] PUBLIC_ENDPOINTS = {
            "/api/api-keys",
            "/api/query-logs",
            "/settings/api-key",
            "/query-logs"
    };

    public static final String STATIC_RESOURCES_PREFIX_CSS = "/css";
    public static final String STATIC_RESOURCES_PREFIX_JS = "/js";
}