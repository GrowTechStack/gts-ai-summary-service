package com.gts.aisummary.global.kafka;

import com.gts.aisummary.global.kafka.dto.SummaryResultMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class SummaryResultProducer {

    static final String TOPIC = "content-summary-result";

    private final KafkaTemplate<String, SummaryResultMessage> kafkaTemplate;

    public void send(SummaryResultMessage message) {
        kafkaTemplate.send(TOPIC, String.valueOf(message.contentId()), message);
        log.debug("[SummaryResultProducer] 요약 결과 produce: contentId={}", message.contentId());
    }
}
