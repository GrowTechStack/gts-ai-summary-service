package com.gts.aisummary.domain.summary.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * AI 요약 요청 DTO
 */
public record SummaryRequest(

        @NotBlank
        String title,

        @NotBlank
        String content
) {}
