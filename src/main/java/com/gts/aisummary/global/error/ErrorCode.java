package com.gts.aisummary.global.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    // Common
    INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, "C001", "잘못된 입력값입니다."),

    // AI Summary
    RATE_LIMIT_EXCEEDED(HttpStatus.TOO_MANY_REQUESTS, "AI001", "Gemini API 요청 한도를 초과했습니다. 잠시 후 재시도합니다."),
    SUMMARY_GENERATION_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "AI002", "AI 요약 생성 중 오류가 발생했습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
