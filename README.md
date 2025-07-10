# 🚀 VYBZ Comment Read Service

<div align="center">

![Java](https://img.shields.io/badge/Java-17-orange?style=for-the-badge&logo=java)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.4.5-green?style=for-the-badge&logo=spring-boot)
![MongoDB](https://img.shields.io/badge/MongoDB-4.4+-blue?style=for-the-badge&logo=mongodb)
![Kafka](https://img.shields.io/badge/Apache_Kafka-2.8+-purple?style=for-the-badge&logo=apache-kafka)
![Swagger](https://img.shields.io/badge/Swagger-3.0-green?style=for-the-badge&logo=swagger)

**댓글 읽기 전용 마이크로서비스**  
*무한 스크롤 기반 댓글 조회 및 실시간 이벤트 처리*

</div>

---

## 📋 Overview

VYBZ Comment Read Service는 소셜 플랫폼의 댓글 시스템을 위한 **읽기 전용 마이크로서비스**입니다. 

### ✨ 주요 기능
- 🔄 **무한 스크롤 댓글 조회** - 성능 최적화된 페이지네이션
- 💬 **대댓글 시스템** - 계층형 댓글 구조 지원
- 📡 **실시간 이벤트 처리** - Kafka를 통한 댓글 CRUD 이벤트 수신
- 🎯 **다중 피드 타입 지원** - REELS, FAN_FEED, NOTICE, ABOUT
- 🔍 **고성능 조회** - MongoDB 기반 최적화된 쿼리

---

## 🛠 Tech Stack

| Category | Technology | Version | Purpose |
|----------|------------|---------|---------|
| **Language** | Java | 17 | Backend Development |
| **Framework** | Spring Boot | 3.4.5 | Application Framework |
| **Database** | MongoDB | 4.4+ | Document Database |
| **Message Queue** | Apache Kafka | 2.8+ | Event Streaming |
| **Service Discovery** | Netflix Eureka | - | Service Registration |
| **API Documentation** | Swagger/OpenAPI | 3.0 | API Documentation |
| **Build Tool** | Gradle | - | Dependency Management |
| **Lombok** | - | - | Boilerplate Code Reduction |

---

## 🏗 Architecture

### 🔄 Service Flow
```
┌─────────────┐    ┌─────────────┐    ┌─────────────┐
│   Client    │───▶│  API Gateway│───▶│Comment Read │
│             │    │             │    │   Service   │
└─────────────┘    └─────────────┘    └─────────────┘
                                              │
                                              ▼
                                    ┌─────────────┐
                                    │   MongoDB   │
                                    │  (Read DB)  │
                                    └─────────────┘
                                              ▲
                                              │
                                    ┌─────────────┐
                                    │    Kafka    │
                                    │ (Events)    │
                                    └─────────────┘
```

---

## 🚀 Quick Start

### Prerequisites
- Java 17+
- MongoDB 4.4+
- Apache Kafka 2.8+
- Gradle 7.0+

### 1. Clone Repository
```bash
git clone <repository-url>
cd comment_read_service
```

### 2. Environment Setup
```bash
# MongoDB 연결 설정
export MONGODB_URI=mongodb://localhost:27017/comment_read_db

# Kafka 설정
export KAFKA_BOOTSTRAP_SERVERS=localhost:9092

# Eureka Server 설정
export EUREKA_CLIENT_SERVICEURL_DEFAULTZONE=http://localhost:8761/eureka/
```

### 3. Build & Run
```bash
# 프로젝트 빌드
./gradlew clean build

# 애플리케이션 실행
./gradlew bootRun
```

### 4. Access Services
- **Application**: http://localhost:8080
- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **Health Check**: http://localhost:8080/actuator/health

---

## 📁 Project Structure

```
comment_read_service/
├── 📄 build.gradle                 # Gradle 빌드 설정
├── 📄 Dockerfile                   # Docker 컨테이너 설정
├── 📄 README.md                    # 프로젝트 문서
├── 📁 gradle/
│   └── 📁 wrapper/
│       ├── 📄 gradle-wrapper.jar
│       └── 📄 gradle-wrapper.properties
├── 📁 src/
│   ├── 📁 main/
│   │   ├── 📁 java/
│   │   │   └── 📁 back/vybz/comment_read_service/
│   │   │       ├── 🚀 CommentReadServiceApplication.java
│   │   │       ├── 📁 comment/
│   │   │       │   ├── 📁 application/          # 비즈니스 로직
│   │   │       │   │   ├── CommentReadService.java
│   │   │       │   │   └── CommentReadServiceImpl.java
│   │   │       │   ├── 📁 domain/               # 도메인 모델
│   │   │       │   │   ├── CommentRead.java
│   │   │       │   │   ├── FeedType.java
│   │   │       │   │   └── WriterType.java
│   │   │       │   ├── 📁 dto/                  # 데이터 전송 객체
│   │   │       │   │   ├── 📁 request/
│   │   │       │   │   └── 📁 response/
│   │   │       │   ├── 📁 infrastructure/       # 데이터 접근 계층
│   │   │       │   │   ├── CommentReadRepository.java
│   │   │       │   │   ├── CommentReadRepositoryCustom.java
│   │   │       │   │   └── CommentReadRepositoryCustomImpl.java
│   │   │       │   └── 📁 presention/           # 컨트롤러 계층
│   │   │       │       └── CommentReadController.java
│   │   │       ├── 📁 common/                   # 공통 모듈
│   │   │       │   ├── 📁 config/               # 설정 클래스
│   │   │       │   ├── 📁 entity/               # 공통 엔티티
│   │   │       │   ├── 📁 exception/            # 예외 처리
│   │   │       │   └── 📁 util/                 # 유틸리티
│   │   │       └── 📁 kafka/                    # Kafka 이벤트 처리
│   │   │           ├── 📁 config/               # Kafka 설정
│   │   │           ├── 📁 consumer/             # 이벤트 컨슈머
│   │   │           └── 📁 event/                # 이벤트 모델
│   │   └── 📁 resources/                        # 설정 파일
│   └── 📁 test/                                 # 테스트 코드
└── 📄 gradlew & gradlew.bat                     # Gradle Wrapper
```

---

## 🔌 API Endpoints

### 댓글 조회 API
| Method | Endpoint | Description |
|--------|----------|-------------|
| `GET` | `/api/v1/read/comments/scroll` | 무한 스크롤 댓글 조회 |
| `GET` | `/api/v1/read/comments/replies/scroll` | 대댓글 무한 스크롤 조회 |

### 요청 파라미터
```json
{
  "feedId": "string",
  "feedType": "REELS|FAN_FEED|NOTICE|ABOUT",
  "lastCreatedAt": "2024-01-01T00:00:00Z",
  "lastId": "string",
  "size": 10
}
```

---

## 🔄 Event Processing

### Kafka Topics
- `comment.create` - 댓글 생성 이벤트
- `comment.update` - 댓글 수정 이벤트  
- `comment.delete` - 댓글 삭제 이벤트
- `comment.like.count` - 댓글 좋아요 수 변경 이벤트

### Event Flow
```
┌─────────────┐    ┌─────────────┐    ┌─────────────┐
│   Comment   │───▶│    Kafka   │───▶│Comment Read │
│   Service   │    │   Topics    │    │   Service   │
└─────────────┘    └─────────────┘    └─────────────┘
```

<div align="center">

**Made with ❤️ by VYBZ Team**

[![GitHub](https://img.shields.io/badge/GitHub-100000?style=for-the-badge&logo=github&logoColor=white)](https://github.com/vybz)
[![LinkedIn](https://img.shields.io/badge/LinkedIn-0077B5?style=for-the-badge&logo=linkedin&logoColor=white)](https://linkedin.com/company/vybz)

</div>
