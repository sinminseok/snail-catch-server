package com.snailcatch.server.config;

import com.snailcatch.server.global.resolver.ApiKeyArgumentResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final ApiKeyArgumentResolver apiKeyArgumentResolver;

    public WebConfig(ApiKeyArgumentResolver apiKeyArgumentResolver) {
        this.apiKeyArgumentResolver = apiKeyArgumentResolver;
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(apiKeyArgumentResolver);
    }
}