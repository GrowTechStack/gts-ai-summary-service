# GrowTechStack AI Summary Service

수집된 기술 블로그 콘텐츠를 분석하여 AI 요약을 생성하고 전달하는 마이크로서비스입니다.

## 기술 스택

| 분류 | 기술 |
|------|------|
| Language | Java 17 |
| Framework | Spring Boot 3.3.5, Spring AI |
| LLM | Groq (Llama 3.1 8B) |
| Messaging | Apache Kafka |
| Service Discovery | Spring Cloud Netflix Eureka Client |
| Docs | SpringDoc OpenAPI (Swagger) |

## 주요 기능

- 기술 블로그 콘텐츠 본문 텍스트 추출 및 정제
- Spring AI를 이용한 콘텐츠 자동 요약 (3줄 요약)
- Kafka 기반 비동기 요약 요청 처리
- 요약 결과 재발행 (`content-summary-result` 토픽)
- 콘텐츠 분석 및 키워드 추출

## Kafka 토픽

| 토픽 | 방향 | 설명 |
|------|------|------|
| `content-summary-request` | 수신 | Collector로부터 요약 요청 수신 |
| `content-summary-result` | 발행 | 요약 완료 후 결과 전송 |

## 환경 변수

| 변수 | 설명 |
|------|------|
| `AI_API_KEY` | Groq/OpenAI API 키 |
| `KAFKA_BOOTSTRAP_SERVERS` | Kafka 브로커 주소 |
| `EUREKA_URL` | Eureka 서버 주소 |

## 로컬 개발

```bash
./gradlew bootRun
```
Swagger UI: `http://localhost:29998/swagger-ui/index.html`
