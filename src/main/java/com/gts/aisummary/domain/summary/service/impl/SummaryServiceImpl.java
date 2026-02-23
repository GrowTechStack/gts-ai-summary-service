package com.gts.aisummary.domain.summary.service.impl;

import com.gts.aisummary.domain.summary.dto.SummaryRequest;
import com.gts.aisummary.domain.summary.dto.SummaryResponse;
import com.gts.aisummary.domain.summary.service.SummaryService;
import com.gts.aisummary.global.error.RateLimitException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

/**
 * Gemini 기반 AI 요약 서비스 구현체
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SummaryServiceImpl implements SummaryService {

    private final ChatClient chatClient;

    private static final String PROMPT_TEMPLATE = """
            당신은 기술 블로그 콘텐츠를 요약하는 전문가입니다.
            아래 기술 블로그 글을 읽고 핵심 내용을 2~3문장으로 한국어 요약해주세요.
            요약문만 반환하고, 불필요한 설명이나 서두는 생략하세요.

            제목: %s

            본문:
            %s
            """;

    @Override
    public SummaryResponse summarize(SummaryRequest request) {
        log.debug("[AI 요약 요청] title={}", request.title());

        String prompt = PROMPT_TEMPLATE.formatted(
                request.title(),
                truncateContent(request.content())
        );

        try {
            String summary = chatClient.prompt()
                    .user(prompt)
                    .call()
                    .content();

            log.debug("[AI 요약 완료] title={}, summary={}", request.title(), summary);
            return SummaryResponse.of(summary);
        } catch (Exception e) {
            if (isRateLimitException(e)) {
                log.warn("[AI 요약] Rate limit 초과, 재시도 대기: title={}", request.title());
                throw new RateLimitException("Gemini API rate limit exceeded", e);
            }
            throw e;
        }
    }

    private boolean isRateLimitException(Throwable e) {
        Throwable cause = e;
        while (cause != null) {
            if (cause.getMessage() != null && cause.getMessage().contains("429")) {
                return true;
            }
            cause = cause.getCause();
        }
        return false;
    }

    /**
     * Gemini API 토큰 한도를 고려해 본문을 최대 3000자로 자릅니다.
     */
    private String truncateContent(String content) {
        if (content == null) return "";
        return content.length() > 3000 ? content.substring(0, 3000) + "..." : content;
    }
}
