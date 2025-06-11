package com.snailcatch.server.auth;

public final class ApiPaths {

    private ApiPaths() {}

    public static final String[] PUBLIC_ENDPOINTS = {
            "/api/api-keys",
            "/api/query-logs",
            "/api/query-logs/*",
            "/settings/api-key",
            "/query-logs",
    };

    public static final String STATIC_RESOURCES_PREFIX_CSS = "/css";
    public static final String STATIC_RESOURCES_PREFIX_JS = "/js";
}