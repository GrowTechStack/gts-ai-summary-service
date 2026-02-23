# AI Summary Service

기술 블로그 콘텐츠를 AI로 요약하는 마이크로서비스입니다.

## 개요

Kafka를 통해 요약 요청을 수신하고, Groq(LLaMA 3.1) API로 요약을 생성한 뒤 결과를 다시 Kafka로 발행합니다.

## 기술 스택

- Java 17 / Spring Boot 3.3.5
- Spring AI 1.1.1 (Groq / LLaMA 3.1)
- Apache Kafka
- Spring Kafka

## Kafka 토픽

| 토픽 | 방향 | 설명 |
|------|------|------|
| `content-summary-request` | 수신 | 요약 요청 |
| `content-summary-result` | 발행 | 요약 결과 |"# gts-ai-summary-service" 
