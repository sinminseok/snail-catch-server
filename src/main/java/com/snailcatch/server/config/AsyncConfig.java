package com.snailcatch.server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@EnableAsync
@Configuration
public class AsyncConfig {

//    @Bean(name = "queryLogTaskExecutor")
//    public Executor queryLogTaskExecutor() {
//        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
//        executor.setCorePoolSize(10);      // 기본 스레드 수
//        executor.setMaxPoolSize(20);       // 최대 스레드 수
//        executor.setQueueCapacity(500);    // 큐에 대기 가능한 작업 개수
//        executor.setThreadNamePrefix("QueryLogExecutor-");
//        executor.initialize();
//        return executor;
//    }

}
