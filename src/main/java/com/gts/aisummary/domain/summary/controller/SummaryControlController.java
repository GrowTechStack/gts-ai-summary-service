package com.gts.aisummary.domain.summary.controller;

import com.gts.aisummary.global.common.response.ApiResult;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.kafka.listener.MessageListenerContainer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * AI 요약 Kafka 소비자의 일시정지/재개를 제어하는 API 컨트롤러
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/summarize")
public class SummaryControlController {

    private final KafkaListenerEndpointRegistry registry;

    @GetMapping("/status")
    public ApiResult<Map<String, Boolean>> getStatus() {
        MessageListenerContainer container = registry.getListenerContainer("summaryConsumer");
        boolean running = container != null && container.isRunning() && !container.isPauseRequested();
        return ApiResult.success(Map.of("running", running));
    }

    @PostMapping("/stop")
    public ApiResult<Map<String, Boolean>> stop() {
        MessageListenerContainer container = registry.getListenerContainer("summaryConsumer");
        if (container != null) {
            container.pause();
        }
        return ApiResult.success(Map.of("running", false));
    }

    @PostMapping("/start")
    public ApiResult<Map<String, Boolean>> start() {
        MessageListenerContainer container = registry.getListenerContainer("summaryConsumer");
        if (container != null) {
            container.resume();
        }
        return ApiResult.success(Map.of("running", true));
    }
}
