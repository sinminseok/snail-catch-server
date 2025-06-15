package com.snailcatch.server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@EnableAsync
@Configuration
public class AsyncConfig {

    @Bean(name = "queryLogExecutor")
    public Executor queryLogExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(10);      // 최소 스레드 개수
        executor.setMaxPoolSize(30);       // 최대 스레드 개수
        executor.setQueueCapacity(10000);  // 작업 큐 크기
        executor.setThreadNamePrefix("QueryLog-");
        executor.initialize();
        return executor;
    }

}
