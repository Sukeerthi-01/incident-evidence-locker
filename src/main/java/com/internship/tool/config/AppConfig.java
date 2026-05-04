package com.internship.tool.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * General application configuration beans.
 *
 * RestTemplate — used by AiServiceClient (Day 4) to call Flask AI service.
 * Timeout: 10 seconds (matches AI service SLA in the spec).
 */
@Configuration
public class AppConfig {

    @Value("${ai.service.timeout-seconds:10}")
    private int aiTimeoutSeconds;

    @Bean
    public RestTemplate restTemplate() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        int timeoutMs = aiTimeoutSeconds * 1000;
        factory.setConnectTimeout(timeoutMs);
        factory.setReadTimeout(timeoutMs);
        return new RestTemplate(factory);
    }
}
