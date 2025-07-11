# VYBZ Comment Read Service

VYBZ 플랫폼의 댓글 조회 기능을 담당하는 마이크로서비스입니다.

## 📋 목차

-   [개요](#개요)
-   [기술 스택](#기술-스택)
-   [주요 기능](#주요-기능)
-   [프로젝트 구조](#프로젝트-구조)
-   [API 문서](#api-문서)
-   [설치 및 실행](#설치-및-실행)
-   [환경 설정](#환경-설정)
-   [댓글 조회 시스템](#댓글-조회-시스템)
-   [이벤트 처리](#이벤트-처리)

## 🎯 개요

VYBZ Comment Read Service는 다음과 같은 기능을 제공합니다:

-   **댓글 조회**: 피드/공지 등의 댓글 목록 조회
-   **무한스크롤**: 커서 기반 무한스크롤 댓글 조회
-   **대댓글 조회**: 특정 댓글의 대댓글 목록 조회
-   **이벤트 처리**: Kafka를 통한 댓글 이벤트 구독
-   **데이터 저장**: MongoDB를 통한 댓글 데이터 저장
-   **서비스 디스커버리**: Eureka Client를 통한 서비스 등록

## 🛠 기술 스택

### Backend

![Spring Cloud](https://img.shields.io/badge/Spring_Cloud-6DB33F?style=for-the-badge&logo=spring&logoColor=white)
![Java](https://img.shields.io/badge/Java-007396?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white)
![MongoDB](https://img.shields.io/badge/MongoDB-47A248?style=for-the-badge&logo=mongodb&logoColor=white)
![Apache Kafka](https://img.shields.io/badge/Apache_Kafka-231F20?style=for-the-badge&logo=apachekafka&logoColor=white)
![Swagger](https://img.shields.io/badge/Swagger-85EA2D?style=for-the-badge&logo=swagger&logoColor=black)
![Gradle](https://img.shields.io/badge/Gradle-02303A?style=for-the-badge&logo=gradle&logoColor=white)

### Infra

![GitHub Actions](https://img.shields.io/badge/GitHub_Actions-2088FF?style=for-the-badge&logo=githubactions&logoColor=white)
![Amazon EC2](https://img.shields.io/badge/Amazon_EC2-FF9900?style=for-the-badge&logo=amazonaws&logoColor=white)
![Docker](https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=docker&logoColor=white)

### 협업

![Discord](https://img.shields.io/badge/Discord-5865F2?style=for-the-badge&logo=discord&logoColor=white)
![Notion](https://img.shields.io/badge/Notion-000000?style=for-the-badge&logo=notion&logoColor=white)
![Git](https://img.shields.io/badge/Git-F05032?style=for-the-badge&logo=git&logoColor=white)

### Database & Cache

-   **MongoDB**: 댓글 데이터 저장 및 조회

### Message Queue

-   **Apache Kafka**: 비동기 이벤트 구독

### Documentation

-   **Swagger/OpenAPI 3.0**: API 문서화

### Build & Deploy

-   **Gradle**: 빌드 도구
-   **Docker**: 컨테이너화

## 🚀 주요 기능

### 1. 댓글 조회 시스템

-   **댓글 목록 조회**: 피드/공지 등에 대한 댓글 목록 조회
-   **무한스크롤**: 커서 기반 무한스크롤로 효율적인 댓글 조회
-   **대댓글 조회**: 특정 댓글의 대댓글 목록 조회
-   **성능 최적화**: 인덱싱을 통한 빠른 조회 성능

### 2. 피드 타입 지원

-   **REELS**: 릴스 피드
-   **NOTICE**: 공지사항
-   **ABOUT**: 소개
-   **FAN_FEED**: 팬 피드

### 3. 작성자 타입 지원

-   **USER**: 일반 사용자
-   **BUSKER**: 버스커

### 4. 이벤트 처리

-   **댓글 생성 이벤트**: 댓글 생성 시 데이터 동기화
-   **댓글 수정 이벤트**: 댓글 수정 시 데이터 업데이트
-   **댓글 삭제 이벤트**: 댓글 삭제 시 데이터 제거
-   **댓글 좋아요 이벤트**: 댓글 좋아요 수 증감 처리

## 📁 프로젝트 구조

```
src/main/java/back/vybz/comment_read_service/
├── common/                    # 공통 모듈
│   ├── config/               # 설정 클래스들
│   │   ├── MongoConfig.java
│   │   ├── ObjectMapperConfig.java
│   │   └── SwaggerConfig.java
│   ├── entity/               # 공통 엔티티
│   │   ├── BaseResponseEntity.java
│   │   └── BaseResponseStatus.java
│   ├── exception/            # 예외 처리
│   │   ├── AsyncExceptionHandler.java
│   │   ├── BaseException.java
│   │   ├── BaseExceptionHandler.java
│   │   ├── BaseExceptionHandlerFilter.java
│   │   └── BaseResponseStatus.java
│   └── util/                 # 유틸리티
│       └── CursorPage.java
├── kafka/                    # Kafka 이벤트 처리
│   ├── config/               # Kafka 설정
│   │   ├── CommentCreateEventKafkaConfig.java
│   │   ├── CommentDeleteEventKafkaConfig.java
│   │   ├── CommentLikeCountEventKafkaConfig.java
│   │   ├── CommentUpdateEventKafkaConfig.java
│   │   └── CommonKafkaConfig.java
│   ├── consumer/             # 이벤트 컨슈머
│   │   ├── CommentCreateEventConsumer.java
│   │   ├── CommentDeleteEventConsumer.java
│   │   ├── CommentLikeCountEventConsumer.java
│   │   └── CommentUpdateEventConsumer.java
│   └── event/                # 이벤트 모델
│       ├── CommentCreateEvent.java
│       ├── CommentDeleteEvent.java
│       ├── CommentLikeCountEvent.java
│       └── CommentUpdateEvent.java
├── comment/                  # 댓글 도메인
│   ├── application/          # 댓글 서비스 로직
│   │   ├── CommentReadService.java
│   │   └── CommentReadServiceImpl.java
│   ├── domain/               # 댓글 도메인 모델
│   │   ├── CommentRead.java
│   │   ├── FeedType.java
│   │   └── WriterType.java
│   ├── dto/                  # 댓글 DTO
│   │   ├── request/
│   │   │   ├── RequestScrollCommentDto.java
│   │   │   └── RequestScrollReplyDto.java
│   │   └── response/
│   │       ├── ResponseScrollCommentDto.java
│   │       └── ResponseScrollReplyDto.java
│   ├── infrastructure/       # 댓글 리포지토리
│   │   ├── CommentReadRepository.java
│   │   ├── CommentReadRepositoryCustom.java
│   │   └── CommentReadRepositoryCustomImpl.java
│   ├── presentation/         # 댓글 컨트롤러
│   │   └── CommentReadController.java
│   └── vo/                   # 댓글 VO
│       └── response/
│           └── ResponseScrollCommentVo.java
└── CommentReadServiceApplication.java
```

## 📚 API 문서

Swagger UI를 통해 API 문서를 확인할 수 있습니다:

-   **URL**: `http://localhost:8000/comment-read-service/swagger-ui/index.html`
-   **API 그룹**: COMMENT-READ-SERVICE

### 주요 API 엔드포인트

#### 댓글 조회 API

-   `GET /api/v1/read/comments/scroll` - 댓글 목록 무한스크롤 조회
-   `GET /api/v1/read/comments/replies/scroll` - 대댓글 무한스크롤 조회

### API 요청/응답 예시

#### 댓글 목록 조회 요청

```
GET /api/v1/read/comments/scroll?feedId=feed-uuid-123&feedType=REELS&size=10
```

#### 대댓글 목록 조회 요청

```
GET /api/v1/read/comments/replies/scroll?parentCommentId=comment-uuid-456&size=10
```

#### 응답 예시

```json
{
    "status": "SUCCESS",
    "message": "댓글 목록 조회 성공",
    "data": {
        "comments": [
            {
                "id": "comment-uuid-123",
                "feedId": "feed-uuid-456",
                "feedType": "REELS",
                "writerUuid": "user-uuid-789",
                "writerType": "USER",
                "comment": "정말 멋진 릴스네요!",
                "parentCommentId": null,
                "likeCount": 5,
                "createdAt": "2024-01-01T12:00:00Z"
            }
        ],
        "hasNext": true,
        "lastCreatedAt": "2024-01-01T12:00:00Z",
        "lastId": "comment-uuid-123"
    }
}
```

## 🚀 설치 및 실행

### 1. 사전 요구사항

-   Java 17
-   Gradle 8.4+
-   Docker (선택사항)
-   MongoDB 6.0+
-   Kafka 3.0+

### 2. 로컬 실행

```bash
# 프로젝트 클론
git clone <repository-url>
cd vybz-comment-read

# Gradle 빌드
./gradlew clean build

# 애플리케이션 실행
./gradlew bootRun
```

### 3. Docker 실행

```bash
# Docker 이미지 빌드
docker build -t vybz-comment-read .

# Docker 컨테이너 실행
docker run -p 8000:8000 vybz-comment-read
```

## ⚙️ 환경 설정

### 주요 설정 파일

-   `application.yml`: 기본 설정

### 환경 변수

```yaml
# MongoDB 설정
spring:
  data:
    mongodb:
      uri: mongodb://${MONGO_USERNAME}:${MONGO_PASSWORD}@${MONGO_HOST}:${MONGO_PORT}/${MONGO_DATABASE}?authSource=admin&replicaSet=myReplicaSet

  kafka:
    bootstrap-servers: ${KAFKA_SERVERS}
    consumer:
      auto-offset-reset: earliest
      key-deserializer: org.apache.kafka.common.serialization.StringDeserializer
      value-deserializer: org.springframework.kafka.support.serializer.JsonDeserializer
```

## 💬 댓글 조회 시스템

#### 피드 타입 (FeedType)

-   **REELS**: 릴스 피드
-   **NOTICE**: 공지사항
-   **ABOUT**: 소개
-   **FAN_FEED**: 팬 피드

#### 작성자 타입 (WriterType)

-   **USER**: 일반 사용자
-   **BUSKER**: 버스커

#### 커스텀 쿼리

```java
// 무한스크롤을 위한 커스텀 쿼리
public List<CommentRead> findCommentsWithScroll(String feedId, FeedType feedType, 
                                               Instant lastCreatedAt, String lastId, int size) {
    Criteria criteria = Criteria.where("feedId").is(feedId)
                               .and("feedType").is(feedType)
                               .and("parentCommentId").isNull();
    
    if (lastCreatedAt != null && lastId != null) {
        criteria.andOperator(
            new Criteria().orOperator(
                Criteria.where("createdAt").lt(lastCreatedAt),
                new Criteria().andOperator(
                    Criteria.where("createdAt").is(lastCreatedAt),
                    Criteria.where("_id").lt(lastId)
                )
            )
        );
    }
    
    Query query = new Query(criteria);
    query.with(Sort.by(Sort.Direction.DESC, "createdAt", "_id"));
    query.limit(size);
    
    return mongoTemplate.find(query, CommentRead.class);
}
```

## 📡 이벤트 처리

### Kafka 이벤트

#### 구독 이벤트

-   **CommentCreateEvent**: 댓글 생성 이벤트

    -   `id`: 댓글 ID
    -   `feedId`: 피드 ID
    -   `feedType`: 피드 타입
    -   `writerUuid`: 작성자 UUID
    -   `writerType`: 작성자 타입
    -   `comment`: 댓글 내용
    -   `parentCommentId`: 부모 댓글 ID
    -   `createdAt`: 생성 시간

-   **CommentUpdateEvent**: 댓글 수정 이벤트

    -   `id`: 댓글 ID
    -   `feedId`: 피드 ID
    -   `feedType`: 피드 타입
    -   `writerUuid`: 작성자 UUID
    -   `writerType`: 작성자 타입
    -   `comment`: 수정된 댓글 내용
    -   `parentCommentId`: 부모 댓글 ID
    -   `updatedAt`: 수정 시간

-   **CommentDeleteEvent**: 댓글 삭제 이벤트

    -   `commentId`: 댓글 ID
    -   `feedId`: 피드 ID

-   **CommentLikeCountEvent**: 댓글 좋아요 수 증감 이벤트
    -   `commentId`: 댓글 ID
    -   `delta`: 증감 값 (+1: 증가, -1: 감소)

### 이벤트 컨슈머

-   `CommentCreateEventConsumer`: 댓글 생성 이벤트 처리
-   `CommentUpdateEventConsumer`: 댓글 수정 이벤트 처리
-   `CommentDeleteEventConsumer`: 댓글 삭제 이벤트 처리
-   `CommentLikeCountEventConsumer`: 댓글 좋아요 수 증감 이벤트 처리

### Kafka 토픽

-   `comment-create-event`: 댓글 생성 이벤트 토픽
-   `comment-update-event`: 댓글 수정 이벤트 토픽
-   `comment-delete-event`: 댓글 삭제 이벤트 토픽
-   `comment-like-count-event`: 댓글 좋아요 수 증감 이벤트 토픽

### 이벤트 처리 시점

-   **댓글 생성**: Comment Service에서 댓글 작성 시
-   **댓글 수정**: Comment Service에서 댓글 수정 시
-   **댓글 삭제**: Comment Service에서 댓글 삭제 시
-   **댓글 좋아요**: Like Service에서 댓글 좋아요 처리 시

## 🏗 아키텍처

### 도메인 주도 설계 (DDD)

-   **Domain Layer**: 댓글 도메인 모델과 비즈니스 로직
-   **Application Layer**: 댓글 조회 서비스 로직과 유스케이스
-   **Infrastructure Layer**: 데이터베이스 접근과 외부 시스템 연동
-   **Presentation Layer**: REST API 엔드포인트

### 마이크로서비스 패턴

-   **Service Discovery**: Eureka Client를 통한 서비스 등록
-   **Event-Driven**: Kafka를 통한 비동기 이벤트 구독
-   **Stateless**: 상태 없는 서비스 설계

### 데이터베이스 설계

-   **MongoDB**: 댓글 데이터 저장 및 조회
-   **인덱싱**: feedId, parentCommentId에 인덱스 설정

### 이벤트 기반 아키텍처

-   **이벤트 구독**: Comment Service의 이벤트를 구독하여 데이터 동기화
-   **비동기 처리**: Kafka를 통한 비동기 이벤트 처리
-   **데이터 일관성**: 이벤트를 통한 데이터 일관성 유지

## 🔧 개발 가이드

### 코드 컨벤션

-   **패키지 구조**: 도메인별 계층 분리
-   **네이밍**: 명확하고 일관된 네이밍 규칙
-   **예외 처리**: BaseException을 통한 통일된 예외 처리
-   **로깅**: Slf4j를 통한 구조화된 로깅

### 테스트

```bash
# 단위 테스트 실행
./gradlew test

# 통합 테스트 실행
./gradlew integrationTest
```

### 예외 처리

```java
// 댓글 관련 예외
public enum BaseResponseStatus {
    COMMENT_NOT_FOUND("댓글을 찾을 수 없습니다."),
    COMMENT_READ_FAIL("댓글 조회에 실패했습니다."),
    INVALID_FEED_TYPE("유효하지 않은 피드 타입입니다.");
}
```

### 성능 최적화

#### 데이터베이스 최적화

-   **인덱싱**: feedId, parentCommentId에 인덱스 설정
-   **커스텀 쿼리**: 무한스크롤을 위한 최적화된 쿼리
-   **페이지네이션**: 커서 기반 페이지네이션으로 성능 향상

#### Kafka 최적화

-   **컨슈머 그룹**: 각 이벤트 타입별 컨슈머 그룹 설정
-   **오프셋 관리**: earliest 오프셋으로 메시지 손실 방지
-   **배치 처리**: 메시지 배치 처리로 성능 향상

## 📊 모니터링

### 로깅

-   **애플리케이션 로그**: Spring Boot 로깅
-   **댓글 조회 로그**: 댓글 조회 작업 로깅
-   **Kafka 로그**: 이벤트 구독 로깅
-   **데이터베이스 로그**: 쿼리 성능 로깅

### 메트릭

-   **댓글 조회량**: 초당 조회 댓글 수
-   **응답 시간**: API 응답 시간
-   **에러율**: 에러 발생률
-   **Kafka 메시지**: 이벤트 구독 성공률

### 알림

-   **댓글 조회 실패**: 댓글 조회 실패 알림
-   **데이터베이스 오류**: DB 연결 오류 알림
-   **Kafka 오류**: 메시지 수신 실패 알림

## 🚨 트러블슈팅

### 일반적인 문제

#### MongoDB 연결 실패

```bash
# MongoDB 연결 확인
mongo mongodb://vybz:<비밀번호>@<탄력적 IP>:27020/vybz?authSource=admin&replicaSet=myReplicaSet

# 복제본 세트 상태 확인
rs.status()
```

#### Kafka 연결 실패

```bash
# Kafka 브로커 상태 확인
kafka-topics.sh --bootstrap-server <탄력적 IP>:10000 --list

# 토픽 상세 정보 확인
kafka-topics.sh --bootstrap-server <탄력적 IP>:10000 --describe --topic comment-create-event
```

#### Eureka 연결 실패

```bash
# Eureka 서버 상태 확인
curl http://eureka:8761/eureka/apps/comment-read-service

# 서비스 등록 확인
curl http://eureka:8761/eureka/apps
```

### 로그 확인

```bash
# 애플리케이션 로그 확인
tail -f logs/application.log

# 에러 로그 확인
grep "ERROR" logs/application.log

# Kafka 로그 확인
grep "Kafka" logs/application.log
```

## 📝 라이선스

이 프로젝트는 VYBZ 팀의 내부 프로젝트입니다.

## 👥 팀

-   **개발팀**: VYBZ Backend Team

---

**VYBZ Comment Read Service** - MongoDB 기반 댓글 조회 서비스
