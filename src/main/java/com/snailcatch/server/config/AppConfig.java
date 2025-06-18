package com.snailcatch.server.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import static com.snailcatch.server.global.constants.PackageConstants.*;

@Configuration
@EnableMongoRepositories(basePackages = MONGO_REPOSITORY_PACKAGE)
@EnableJpaRepositories(basePackages = JPA_REPOSITORY_PACKAGE)
@EntityScan(basePackages = ENTITY_SCAN_PACKAGE)
public class AppConfig {
}
