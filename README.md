# Gallery Reservation

미술관 갤러리 공간 예약 및 관리 웹 애플리케이션입니다.
사용자는 갤러리를 조회하고 날짜·시간·인원을 선택해 예약을 신청할 수 있으며, 관리자는 갤러리와 예약을 통합 관리합니다.

---

## 기술 스택

| 분류 | 기술 |
|------|------|
| Language | Java 21 |
| Framework | Spring Boot 3.4.1 |
| Security | Spring Security 6 + OAuth2 Client |
| ORM | Spring Data JPA (Hibernate) |
| View | Thymeleaf |
| Database | PostgreSQL (Supabase) |
| Build | Gradle |
| Etc | Lombok |

---

## 주요 기능

### 회원
- 일반 회원가입 / 로그인 (BCrypt 암호화)
- 소셜 로그인 — 카카오, 네이버 OAuth2 지원
- 소셜 최초 로그인 시 자동 회원 등록

### 갤러리 조회 (공개)
- 전체 갤러리 목록 조회
- 커버 이미지, 위치, 수용 인원, 운영 시간 확인
- 갤러리 상세 페이지에서 예약 신청

### 예약
- 날짜 / 30분 단위 시간 슬롯 / 인원 / 연락처 입력
- 내 예약 목록 조회 (페이징, 갤러리명 검색, 상태 필터)
- 예약 상세 페이지 (갤러리 커버 이미지 포함)
- 대기 중인 예약 취소 신청
- 예약 상태: `PENDING(대기) → APPROVED(승인) / REJECTED(거절) / CANCELLED(취소)`

### 할 일 (Todo)
- 개인 할 일 등록 / 수정 / 삭제
- 마감일, 완료 여부 관리
- 키워드 검색 + 완료 여부 필터 + 페이징

### 관리자
- 갤러리 등록 / 수정 / 삭제
- 운영 상태 토글 (운영중 / 비활성화)
- 전체 예약 목록 조회 (페이징, 갤러리명 검색, 상태 필터)
- 예약 승인 / 거절 처리

---

## 권한 구조

| 역할 | 접근 가능 기능 |
|------|---------------|
| 비로그인 | 메인, 갤러리 목록 / 상세 |
| `ROLE_USER` | 갤러리 예약 신청, 내 예약 조회 / 취소, 할 일 관리 |
| `ROLE_ADMIN` | 위 전체 + 갤러리 관리, 전체 예약 승인/거절 |

---

## 프로젝트 구조

```
src/main/java/com/study/galleryreservation/
│
├── config/
│   ├── SecurityConfig.java                     # Spring Security 설정 (CSRF, OAuth2, 권한)
│   ├── CustomAuthenticationSuccessHandler.java  # 로그인 성공 후 세션 처리
│   ├── OAuthAttributes.java                    # OAuth2 공급자별 속성 매핑
│   └── CustomUserDetailsService.java
│
├── controller/
│   ├── ViewController.java          # GET /  (메인), GET /gallery/list
│   ├── MemberController.java        # 회원가입 / 로그인
│   ├── GalleryController.java       # 갤러리 상세 조회 / 예약 등록
│   ├── ReservationController.java   # 예약 목록 / 상세 / 취소
│   ├── TodoController.java          # 할 일 CRUD
│   └── AdminController.java         # 갤러리 관리 / 예약 승인·거절
│
├── domain/
│   ├── gallery/
│   │   └── Gallery.java
│   ├── member/
│   │   ├── Member.java
│   │   └── MemberRole.java          # ROLE_USER, ROLE_ADMIN
│   ├── reservation/
│   │   ├── Reservation.java
│   │   └── ReservationStatus.java   # PENDING, APPROVED, REJECTED, CANCELLED
│   ├── todo/
│   │   └── Todo.java
│   └── session/
│       ├── SessionUser.java
│       └── SnsUser.java
│
├── dto/
│   ├── gallery/    GalleryCreateRequestDto / GalleryUpdateRequestDto / GalleryResponseDto
│   ├── member/     MemberJoinRequestDto / MemberLoginRequestDto
│   ├── reservation/ ReservationCreateRequestDto / ReservationResponseDto
│   └── todo/       TodoCreateRequestDto / TodoUpdateRequestDto / TodoResponseDto
│
├── repository/
│   ├── GalleryRepository.java       # 이름·위치 키워드 검색
│   ├── MemberRepository.java
│   ├── ReservationRepository.java   # 회원별 / 갤러리명 키워드 검색
│   ├── TodoRepository.java
│   └── SnsUserRepository.java
│
└── service/
    ├── GalleryService.java
    ├── MemberService.java
    ├── ReservationService.java
    ├── TodoService.java
    └── CustomOAuth2UserService.java

src/main/resources/
├── templates/
│   ├── index.html
│   ├── member/          login.html / join.html
│   ├── gallery/         list.html / detail.html
│   ├── reservation/     list.html / reservation-detail.html
│   ├── todo/            list.html / form.html / update.html
│   └── admin/           gallery-list.html / gallery-form.html / gallery-edit.html / reservation-list.html
├── static/css/
│   └── common.css
├── application.yml
└── db.sql               # 테이블 DDL
```

---

## ERD

```
member  (1) ──────< todo        (N)
member  (1) ──────< reservation (N)
gallery (1) ─────< reservation  (N)
```

| 테이블 | 주요 컬럼 |
|--------|-----------|
| `member` | id, username, password, email, role, created_at |
| `gallery` | id, name, location, floor_zone, description, capacity, is_active, start_time, end_time, cover_image_url |
| `reservation` | id, member_id, gallery_id, reservation_date, start_time, end_time, guests, contact, status, created_at |
| `todo` | id, member_id, title, content, is_done, due_date, created_at |

---

## URL 목록

### 공개
| URL | 설명 |
|-----|------|
| `GET /` | 메인 페이지 |
| `GET /gallery/list` | 갤러리 목록 |
| `GET /gallery/detail?id={id}` | 갤러리 상세 + 예약 폼 |
| `GET /member/login` | 로그인 |
| `GET /member/join` | 회원가입 |

### 로그인 사용자
| URL | 설명 |
|-----|------|
| `POST /gallery/detail` | 예약 등록 |
| `GET /reservation/list` | 내 예약 목록 (페이징, 검색) |
| `GET /reservation/detail/{id}` | 예약 상세 |
| `POST /reservation/cancel/{id}` | 예약 취소 |
| `GET /todo/list` | 할 일 목록 |
| `GET /todo/form` | 할 일 등록 |
| `POST /todo/create` | 할 일 저장 |
| `GET /todo/update/{id}` | 할 일 수정 |
| `POST /todo/delete/{id}` | 할 일 삭제 |

### 관리자 전용 (`/admin/**`)
| URL | 설명 |
|-----|------|
| `GET /admin/gallery/list` | 갤러리 목록 (페이징, 검색) |
| `GET /admin/gallery/form` | 갤러리 등록 폼 |
| `POST /admin/gallery/form` | 갤러리 등록 |
| `GET /admin/gallery/edit/{id}` | 갤러리 수정 폼 |
| `POST /admin/gallery/edit/{id}` | 갤러리 수정 |
| `POST /admin/gallery/delete/{id}` | 갤러리 삭제 |
| `GET /admin/reservation/list` | 전체 예약 목록 (페이징, 검색) |
| `POST /admin/reservation/approve/{id}` | 예약 승인 |
| `POST /admin/reservation/reject/{id}` | 예약 거절 |

---

## 소셜 로그인

카카오, 네이버 OAuth2를 지원합니다. 최초 소셜 로그인 시 `member` 테이블에 자동 등록됩니다.

| Provider | username 형식 |
|----------|--------------|
| 카카오 | `kakao_{providerId}` |
| 네이버 | `naver_{providerId}` |

---

## 실행 방법

### 1. application.yml 설정

```yaml
spring:
  datasource:
    url: jdbc:postgresql://{supabase_host}:6543/postgres
    username: {db_username}
    password: {db_password}
  security:
    oauth2:
      client:
        registration:
          kakao:
            client-id: {kakao_client_id}
            client-secret: {kakao_client_secret}
          naver:
            client-id: {naver_client_id}
            client-secret: {naver_client_secret}
```

### 2. 데이터베이스 초기화

```bash
# src/main/resources/db.sql 파일을 PostgreSQL에 실행
```

### 3. 빌드 및 실행

```bash
./gradlew bootRun
```

`http://localhost:8080` 접속

---

## 보안

- **CSRF**: `CookieCsrfTokenRepository` 사용, 모든 POST 요청에 CSRF 토큰 필요
- **비밀번호**: `BCryptPasswordEncoder(strength: 12)` 암호화
- **세션**: 로그인 성공 시 `SessionUser` 객체를 HTTP 세션에 저장
- **권한 검증**: 예약·할 일은 본인 데이터만 조회·수정·삭제 가능
