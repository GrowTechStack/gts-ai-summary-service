package com.gts.aisummary.global.kafka;

import com.gts.aisummary.domain.summary.dto.SummaryRequest;
import com.gts.aisummary.domain.summary.dto.SummaryResponse;
import com.gts.aisummary.domain.summary.service.SummaryService;
import com.gts.aisummary.global.error.RateLimitException;
import com.gts.aisummary.global.kafka.dto.SummaryRequestMessage;
import com.gts.aisummary.global.kafka.dto.SummaryResultMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class SummaryRequestConsumer {

    private final SummaryService summaryService;
    private final SummaryResultProducer summaryResultProducer;

    @KafkaListener(id = "summaryConsumer", topics = "content-summary-request", groupId = "ai-summary-service")
    public void consume(SummaryRequestMessage message) {
        log.info("[SummaryRequestConsumer] 요약 요청 수신: contentId={}, title={}", message.contentId(), message.title());
        try {
            SummaryResponse response = summaryService.summarize(new SummaryRequest(message.title(), message.content()));
            log.info("[SummaryRequestConsumer] 요약 완료: contentId={}", message.contentId());
            summaryResultProducer.send(new SummaryResultMessage(message.contentId(), response.summary()));
        } catch (RateLimitException e) {
            throw e;
        } catch (Exception e) {
            log.error("[SummaryRequestConsumer] 요약 실패: contentId={}, 원인={}", message.contentId(), e.getMessage());
        }
    }
}
