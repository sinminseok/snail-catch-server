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
import java.util.List;

import static com.snailcatch.server.auth.ApiPaths.*;

@Component
public class ApiKeyFilter extends OncePerRequestFilter {

    private static final String HEADER_API_KEY = "X-API-KEY";
    private static final String ERROR_MESSAGE = "Invalid or missing API Key";
    private static final int UNAUTHORIZED = HttpServletResponse.SC_UNAUTHORIZED;

    private final ApiKeyService apiKeyService;
    private final List<String> publicEndpoints = List.of(PUBLIC_ENDPOINTS);

    public ApiKeyFilter(ApiKeyService apiKeyService) {
        this.apiKeyService = apiKeyService;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        String path = request.getRequestURI();

        if (isWhitelisted(path)) {
            filterChain.doFilter(request, response);
            return;
        }

        String apiKey = request.getHeader(HEADER_API_KEY);

        if (isInvalidApiKey(apiKey)) {
            rejectRequest(response);
            return;
        }

        authenticate(apiKey);
        filterChain.doFilter(request, response);
    }

    private boolean isWhitelisted(String path) {
        return publicEndpoints.contains(path)
                || path.startsWith(STATIC_RESOURCES_PREFIX_CSS)
                || path.startsWith(STATIC_RESOURCES_PREFIX_JS);
    }

    private boolean isInvalidApiKey(String apiKey) {
        return apiKey == null || !apiKeyService.isValid(apiKey);
    }

    private void rejectRequest(HttpServletResponse response) throws IOException {
        response.setStatus(UNAUTHORIZED);
        response.getWriter().write(ERROR_MESSAGE);
    }

    private void authenticate(String apiKey) {
        Authentication authentication = new UsernamePasswordAuthenticationToken(apiKey, null, List.of());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
