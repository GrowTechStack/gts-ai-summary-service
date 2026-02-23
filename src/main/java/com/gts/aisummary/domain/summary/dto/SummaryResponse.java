package com.gts.aisummary.domain.summary.dto;

/**
 * AI 요약 응답 DTO
 */
public record SummaryResponse(
        String summary
) {
    public static SummaryResponse of(String summary) {
        return new SummaryResponse(summary);
    }
}
