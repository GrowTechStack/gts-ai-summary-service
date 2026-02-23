package com.gts.aisummary.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.util.backoff.FixedBackOff;

@Configuration
public class KafkaConfig {

    /**
     * Rate limit(429) 예외 발생 시 60초 간격으로 최대 5회 재시도합니다.
     * Consumer에서 RateLimitException만 re-throw하므로 해당 케이스만 이 핸들러에 도달합니다.
     */
    @Bean
    public DefaultErrorHandler kafkaErrorHandler() {
        return new DefaultErrorHandler(new FixedBackOff(60_000L, 5L));
    }
}
