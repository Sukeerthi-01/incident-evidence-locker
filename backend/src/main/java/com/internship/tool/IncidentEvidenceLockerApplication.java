package com.internship.tool;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Tool-36 — Incident Evidence Locker
 * Main application entry point.
 *
 * Annotations:
 *  @EnableCaching    — activates Redis @Cacheable / @CacheEvict
 *  @EnableJpaAuditing — activates @CreatedDate / @LastModifiedDate on entities
 *  @EnableAsync      — allows @Async methods (AI calls on create)
 *  @EnableScheduling — activates @Scheduled (daily email reminders)
 */
@SpringBootApplication
@EnableCaching
@EnableJpaAuditing
@EnableAsync
@EnableScheduling
public class IncidentEvidenceLockerApplication {

    public static void main(String[] args) {
        SpringApplication.run(IncidentEvidenceLockerApplication.class, args);
    }
}
