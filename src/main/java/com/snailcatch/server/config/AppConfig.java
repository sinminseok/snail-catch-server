package com.snailcatch.server.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = {"com.snailcatch.server.domain"})
@EnableJpaRepositories(basePackages = "com.snailcatch.server.domain")
@EntityScan(basePackages = "com.snailcatch.server.domain")
public class AppConfig {
}
