package com.gts.aisummary.domain.summary.service;

import com.gts.aisummary.domain.summary.dto.SummaryRequest;
import com.gts.aisummary.domain.summary.dto.SummaryResponse;

/**
 * AI 요약 서비스 인터페이스
 */
public interface SummaryService {

    /**
     * 제목과 본문을 받아 2~3문장의 한국어 요약문을 생성합니다.
     */
    SummaryResponse summarize(SummaryRequest request);
}
