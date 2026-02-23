package com.gts.aisummary.domain.summary.controller;

import com.gts.aisummary.domain.summary.dto.SummaryRequest;
import com.gts.aisummary.domain.summary.dto.SummaryResponse;
import com.gts.aisummary.domain.summary.service.SummaryService;
import com.gts.aisummary.global.common.response.ApiResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * AI 요약 API 컨트롤러
 */
@Tag(name = "Summary", description = "AI 콘텐츠 요약 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/summarize")
public class SummaryController {

    private final SummaryService summaryService;

    /**
     * 제목과 본문을 받아 AI 요약문을 반환합니다.
     */
    @Operation(summary = "AI 요약 생성", description = "기술 블로그 제목과 본문을 받아 2~3문장의 한국어 요약문을 생성합니다.")
    @PostMapping
    public ResponseEntity<ApiResult<SummaryResponse>> summarize(@Valid @RequestBody SummaryRequest request) {
        return ResponseEntity.ok(ApiResult.success(summaryService.summarize(request)));
    }
}
