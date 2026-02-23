package com.gts.aisummary.global.error;

import com.gts.aisummary.global.common.response.ApiResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResult<?>> handleValidation(MethodArgumentNotValidException e) {
        String message = e.getBindingResult().getFieldErrors().stream()
                .map(err -> err.getField() + ": " + err.getDefaultMessage())
                .findFirst().orElse(ErrorCode.INVALID_INPUT_VALUE.getMessage());
        return ResponseEntity.badRequest()
                .body(ApiResult.fail(ErrorCode.INVALID_INPUT_VALUE.getCode(), message));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResult<?>> handleException(Exception e) {
        if (isRateLimitException(e)) {
            log.warn("[AI 요약 Rate Limit] Gemini API 요청 한도 초과");
            ErrorCode errorCode = ErrorCode.RATE_LIMIT_EXCEEDED;
            return ResponseEntity.status(errorCode.getStatus())
                    .body(ApiResult.fail(errorCode.getCode(), errorCode.getMessage()));
        }
        log.error("[AI 요약 오류]", e);
        ErrorCode errorCode = ErrorCode.SUMMARY_GENERATION_FAILED;
        return ResponseEntity.status(errorCode.getStatus())
                .body(ApiResult.fail(errorCode.getCode(), errorCode.getMessage()));
    }

    private boolean isRateLimitException(Throwable e) {
        Throwable cause = e;
        while (cause != null) {
            String message = cause.getMessage();
            if (message != null && (message.contains("429") || message.contains("quota") || message.contains("rate limit"))) {
                return true;
            }
            cause = cause.getCause();
        }
        return false;
    }
}
