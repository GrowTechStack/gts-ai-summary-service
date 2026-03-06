# GrowTechStack AI Summary Service

수집된 기술 블로그 콘텐츠를 AI로 요약하는 마이크로서비스입니다.

## 기술 스택

| 분류 | 기술 |
|------|------|
| Language | Java 17 |
| Framework | Spring Boot 3.3.5 |
| AI | Spring AI (OpenAI 호환) + Groq API (Llama 3.1 8B Instant) |
| Messaging | Apache Kafka (Confluent Cloud) |
| Service Discovery | Spring Cloud Netflix Eureka Client |
| Docs | SpringDoc OpenAPI (Swagger) |

## 주요 기능

- Kafka `content-summary-request` 토픽에서 요약 요청 비동기 수신
- Groq API(Llama 3.1 8B)로 콘텐츠 본문 3줄 요약 생성
- 요약 결과를 `content-summary-result` 토픽으로 발행 → Collector가 DB 업데이트

## Kafka 토픽

| 토픽 | 방향 | 설명 |
|------|------|------|
| `content-summary-request` | 수신 | Collector로부터 요약 요청 수신 |
| `content-summary-result` | 발행 | 요약 완료 결과 전송 |

## 환경 변수

| 변수 | 설명 |
|------|------|
| `AI_API_KEY` | Groq API 키 |
| `KAFKA_BOOTSTRAP_SERVERS` | Kafka 브로커 주소 |
| `KAFKA_USERNAME` | Kafka SASL 사용자명 |
| `KAFKA_PASSWORD` | Kafka SASL 비밀번호 |
| `EUREKA_URL` | Eureka 서버 주소 |

## 배포

`main` 브랜치 push → GitHub Actions → ECR push → EC2 자동 배포
