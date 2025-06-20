package com.snailcatch.server.auth;

import com.snailcatch.server.domain.api_key.service.ApiKeyService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;

import static com.snailcatch.server.auth.ApiPaths.*;

@Component
public class ApiKeyFilter extends OncePerRequestFilter {

    private static final String HEADER_API_KEY = "X-API-KEY";

    private final ApiKeyService apiKeyService;

    public ApiKeyFilter(ApiKeyService apiKeyService) {
        this.apiKeyService = apiKeyService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String path = request.getRequestURI();
        if (isWhitelisted(path)) {
            filterChain.doFilter(request, response);
            return;
        }
        String apiKey = request.getHeader(HEADER_API_KEY);
        if (apiKey == null || !apiKeyService.isValid(apiKey)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Invalid or missing API Key");
            return;
        }

        Authentication authentication = new UsernamePasswordAuthenticationToken(apiKey, null, Collections.emptyList());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request, response);
    }


    private boolean isWhitelisted(String path) {
        return Arrays.asList(PUBLIC_ENDPOINTS).contains(path)
                || path.startsWith(STATIC_RESOURCES_PREFIX_CSS)
                || path.startsWith(STATIC_RESOURCES_PREFIX_JS);
    }
}
