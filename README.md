# 🖼️ Art Museum - 미술관 갤러리 예약 플랫폼

## 🎨 프로젝트 소개

🏷 **프로젝트 명 : Art Museum**

🗓️ **프로젝트 기간 : 2026.03 ~ 2026.04.08**

👥 **구성원 : 김태혁(팀장👑), 김민준, 이유리, 박준현**

---

### ✅ 배포 주소

http://

### ✅ 기획 배경

> "미술관을 더 편하게, 더 스마트하게"

평소 미술관에 관심이 많아 갤러리 공간을 직접 예약하고 관리할 수 있는 서비스를 기획하게 되었습니다.
사용자는 원하는 갤러리 공간을 탐색하고 날짜와 시간을 선택해 예약을 신청할 수 있으며,
관리자는 갤러리 등록부터 예약 승인까지 통합적으로 관리할 수 있습니다.

---

### ✅ 서비스 소개

> 미술관 갤러리 공간을 온라인으로 예약할 수 있는 플랫폼

- 갤러리 공간을 탐색하고 날짜·시간·인원을 선택해 갤러리를 예약 신청할 수 있다.
- 관리자는 갤러리를 등록/수정하고 예약을 승인/거절할 수 있다.
- 카카오, 네이버 소셜 로그인을 지원한다.

---

### 👥 서비스 대상

- 미술관 갤러리 공간을 대관하고 싶은 사람들
- 전시 공간을 편리하게 예약하고 싶은 사람들

---

## 🛠 기술 스택

### Backend

<p>
  <img src="https://img.shields.io/badge/Java 21-007396?style=flat-square&logo=OpenJDK&logoColor=white"/>
  <img src="https://img.shields.io/badge/Spring Boot 3-6DB33F?style=flat-square&logo=springboot&logoColor=white"/>
  <img src="https://img.shields.io/badge/Spring Security 6-6DB33F?style=flat-square&logo=springsecurity&logoColor=white"/>
  <img src="https://img.shields.io/badge/Spring Data JPA-6DB33F?style=flat-square&logo=spring&logoColor=white"/>
  <img src="https://img.shields.io/badge/OAuth2-EB5424?style=flat-square&logo=auth0&logoColor=white"/>
</p>

### Frontend

<p>
  <img src="https://img.shields.io/badge/Thymeleaf-005F0F?style=flat-square&logo=thymeleaf&logoColor=white"/>
  <img src="https://img.shields.io/badge/HTML5-E34F26?style=flat-square&logo=html5&logoColor=white"/>
  <img src="https://img.shields.io/badge/CSS3-1572B6?style=flat-square&logo=css3&logoColor=white"/>
  <img src="https://img.shields.io/badge/JavaScript-F7DF1E?style=flat-square&logo=javascript&logoColor=black"/>
</p>

### Database

<p>
  <img src="https://img.shields.io/badge/PostgreSQL-4169E1?style=flat-square&logo=postgresql&logoColor=white"/>
  <img src="https://img.shields.io/badge/Supabase-3ECF8E?style=flat-square&logo=supabase&logoColor=white"/>
</p>

### Build & Deploy

<p>
  <img src="https://img.shields.io/badge/Gradle-02303A?style=flat-square&logo=gradle&logoColor=white"/>
  <img src="https://img.shields.io/badge/Docker-2496ED?style=flat-square&logo=docker&logoColor=white"/>
  <img src="https://img.shields.io/badge/AWS EC2-FF9900?style=flat-square&logo=amazonec2&logoColor=white"/>
</p>

### Collaboration

<p>
  <img src="https://img.shields.io/badge/GitHub-181717?style=flat-square&logo=github&logoColor=white"/>
  <img src="https://img.shields.io/badge/Discord-5865F2?style=flat-square&logo=discord&logoColor=white"/>
  <img src="https://img.shields.io/badge/Notion-000000?style=flat-square&logo=notion&logoColor=white"/>
</p>

---

## ✨ 핵심 기능 (사용자 시나리오 중심)

### 👤 일반 사용자 시나리오

#### 1단계 — 회원가입 / 로그인

- 아이디·비밀번호로 직접 회원가입하거나, **카카오 · 네이버 소셜 로그인**으로 간편하게 가입할 수 있습니다.
- 소셜 로그인 시 최초 1회 자동 회원가입 처리되며, 이후 동일 이메일로 재접속하면 기존 계정과 연동됩니다.
- 비로그인 사용자도 갤러리 목록·상세 페이지는 자유롭게 조회할 수 있으며, 예약 신청 시점에 로그인 페이지로 안내됩니다.

#### 2단계 — 갤러리 탐색

- 전체 갤러리 목록을 **커버 이미지, 위치, 수용 인원, 운영 상태**와 함께 카드 형태로 탐색합니다.
- 갤러리명으로 키워드 검색이 가능하며, 운영 중인 갤러리만 예약 신청이 활성화됩니다.

#### 3단계 — 예약 신청

- 갤러리 상세 페이지에서 **날짜, 30분 단위 시간 슬롯, 예약 인원, 연락처**를 선택해 예약을 신청합니다.
- 갤러리의 수용 인원을 초과하는 예약은 서비스 계층에서 검증하여 차단합니다.
- 예약 신청 직후 상태는 `PENDING(대기)` 이며, 관리자의 승인을 기다립니다.

#### 4단계 — 예약 내역 확인 · 취소

- 내 예약 목록에서 **예약 상태(대기 / 확정 / 거절 / 취소), 갤러리 정보, 예약 날짜·시간**을 한눈에 확인합니다.
- `PENDING` 상태의 예약에 한해 직접 취소할 수 있으며, 취소 시 갤러리 수용 인원이 자동으로 복원됩니다.

---

### 🛠️ 관리자 시나리오

#### 1단계 — 갤러리 관리

- 갤러리를 **등록·수정·삭제**하고, 운영 상태(운영중 / 비활성화)를 관리합니다.
- 갤러리 등록 시 이름, 위치, 층/구역, 수용 인원, 운영 시간, 커버 이미지 URL, 소개를 입력하며, 중복 이름은 등록이 거부됩니다.

#### 2단계 — 예약 승인 · 거절

- 전체 예약 목록을 조회하고, `PENDING` 상태의 예약을 **승인(APPROVED)** 또는 **거절(REJECTED)** 처리합니다.
- 승인 시 갤러리 수용 인원이 예약 인원만큼 차감되며, 거절 시에는 차감 없이 상태만 변경됩니다.

```
예약 상태 흐름
PENDING(대기) ──→ APPROVED(확정)
             └──→ REJECTED(거절)
             └──→ CANCELLED(취소, 사용자 직접)
```

#### 3단계 — 할 일(Todo) 관리

- 전시 준비, 공간 점검, 공지 작성 등 **운영 업무를 할 일 목록**으로 등록하고 관리합니다.
- 제목 키워드 검색, 완료/미완료 필터, 마감일 설정을 지원하며 ADMIN 권한 전용 기능입니다.

---

## 💌 서비스 화면 및 기능 소개

### ✅ 메인

![메인 페이지](readme_assets/main1.png)

![메인 페이지](readme_assets/main2.png)

![메인 페이지](readme_assets/main3.png)

---

### ✅ 회원

- **회원가입**
  > 이메일과 비밀번호로 회원가입 및 로그인할 수 있다.

![회원가입](readme_assets/join.png)

- **로그인 / 소셜 로그인**
  > 카카오, 네이버 OAuth2 소셜 로그인을 지원한다.

![로그인](readme_assets/login.png)

---

### ✅ 갤러리 조회

- **갤러리 목록 조회**
  > 전체 갤러리 목록을 커버 이미지, 위치, 수용 인원과 함께 확인할 수 있다.

![갤러리 목록](readme_assets/gallery-list1.png)
![갤러리 목록](readme_assets/gallery-list2.png)

- **갤러리 상세 조회 및 예약 신청**
  > 갤러리 상세 페이지에서 날짜, 30분 단위 시간 슬롯, 인원, 연락처를 선택해 바로 예약 신청할 수 있다.

![갤러리 예약 신청](readme_assets/gallery-detail1.png)
![갤러리 예약 신청](readme_assets/gallery-detail2.png)

---

### ✅ 예약 관리

- **내 예약 목록 조회**
  > 신청한 예약 목록을 페이지네이션과 갤러리명 검색으로 조회할 수 있고, 대기 중인 예약을 취소 신청할 수 있다.

![예약 조회](readme_assets/reservation-list.png)

> `대기중 → 승인 / 거절 / 취소`

- **예약 상세 조회**
  > 갤러리 커버 이미지와 함께 예약 정보를 상세하게 확인할 수 있다.

![예약 상세 보기](readme_assets/reservation-detail1.png)
![예약 상세 보기](readme_assets/reservation-detail2.png)
![예약 상세 보기](readme_assets/reservation-detail3.png)

---

### ✅ 관리자 페이지

- **갤러리 관리**
  > 갤러리 등록, 수정, 삭제 및 운영 상태(운영중/비활성화)를 관리할 수 있다.

![관리자 갤러리 관리](readme_assets/admin-gallery.png)

- **갤러리 등록**
  > 갤러리 등록, 수정, 삭제 및 운영 상태(운영중/비활성화)를 관리할 수 있다.

![관리자 갤러리 등록](readme_assets/admin-gallery-add.png)

- **갤러리 수정**
  > 갤러리 등록, 수정, 삭제 및 운영 상태(운영중/비활성화)를 관리할 수 있다.

![관리자 갤러리 수정](readme_assets/admin-gallery-edit.png)

- **예약 승인/거절**
  > 전체 예약 목록을 조회하고 예약을 승인하거나 거절할 수 있다.

![관리자 예약 관리](readme_assets/admin-reservation.png)

---

### ✅ 할일 페이지

- **할일 등록/수정**
  > 전체 예약 목록을 조회하고 예약을 승인하거나 거절할 수 있다.

![할일 리스트](readme_assets/todo-list.png)
![할일 등록](readme_assets/todo-add.png)
![할일 수정](readme_assets/todo-edit.png)

---

## 📡 API 명세서

![API 명세서](readme_assets/api.png)

---

## 🏗 시스템 아키텍처

![아키텍처 설계](readme_assets/architecture.png)

---

## 🗂 프로젝트 구조

```
src/main/java/com/study/galleryreservation/
├── config/                         # 설정 클래스
│   ├── SecurityConfig.java         # Spring Security 설정
│   ├── CustomAuthenticationSuccessHandler.java
│   └── OAuthAttributes.java        # OAuth2 속성 매핑
│
├── controller/                     # 컨트롤러
│   ├── AdminController.java        # 관리자 전용 (갤러리 관리, 예약 승인)
│   ├── GalleryController.java      # 갤러리 상세 / 예약 신청
│   ├── MemberController.java       # 회원가입 / 로그인
│   ├── ReservationController.java  # 예약 신청 / 조회 / 취소
│   ├── TodoController.java         # 할 일 CRUD
│   └── ViewController.java         # 공통 뷰 라우팅
│
├── domain/                         # 엔티티
│   ├── gallery/Gallery.java
│   ├── member/Member.java
│   ├── member/MemberRole.java
│   ├── reservation/Reservation.java
│   ├── reservation/ReservationStatus.java
│   ├── session/SessionUser.java
│   ├── session/SnsUser.java
│   ├── session/UserRole.java
│   └── todo/Todo.java
│
├── dto/                            # DTO
│   ├── gallery/
│   ├── member/
│   ├── reservation/
│   └── todo/
│
├── repository/                     # JPA 레포지토리
│   ├── GalleryRepository.java
│   ├── MemberRepository.java
│   ├── ReservationRepository.java
│   ├── SnsUserRepository.java
│   └── TodoRepository.java
│
└── service/                        # 서비스
    ├── CustomOAuth2UserService.java
    ├── CustomUserDetailsService.java
    ├── GalleryService.java
    ├── MemberService.java
    ├── ReservationService.java
    └── TodoService.java

src/main/resources/
├── templates/
│   ├── index.html                  # 메인 페이지
│   ├── admin/                      # 관리자 페이지 (갤러리 관리·예약 승인)
│   ├── gallery/                    # 갤러리 목록/상세
│   ├── member/                     # 로그인/회원가입
│   ├── reservation/                # 예약 목록/상세
│   ├── todo/                       # 할 일 목록/폼/수정
│   └── visit/                      # 관람시간/오시는길
├── static/
│   ├── css/common.css
│   ├── admin/ gallery/ member/ reservation/ todo/  # 페이지별 CSS
│   └── favicon.png
├── application.yml
└── application-secret.yml
```

---

## 📜 프로젝트 산출물

### ERD

```mermaid
erDiagram
    MEMBER {
        BIGINT id PK
        VARCHAR username UK
        VARCHAR password
        VARCHAR email UK
        VARCHAR role
        TIMESTAMP created_at
    }

    GALLERY {
        BIGINT id PK
        VARCHAR name
        VARCHAR location
        VARCHAR floor_zone
        TEXT description
        INT capacity
        BOOLEAN is_active
        TIME start_time
        TIME end_time
        VARCHAR cover_image_url
        TIMESTAMP created_at
        TIMESTAMP updated_at
    }

    RESERVATION {
        BIGINT id PK
        BIGINT member_id FK
        BIGINT gallery_id FK
        DATE reservation_date
        TIME start_time
        TIME end_time
        INT guests
        VARCHAR contact_info
        VARCHAR status
        TIMESTAMP created_at
        TIMESTAMP updated_at
    }

    TODO {
        BIGINT id PK
        BIGINT member_id FK
        VARCHAR title
        TEXT content
        BOOLEAN is_done
        DATE due_date
        TIMESTAMP created_at
        TIMESTAMP updated_at
    }

    SNS_USER {
        BIGINT id PK
        VARCHAR provider
        VARCHAR provider_id
        VARCHAR email
        VARCHAR name
        VARCHAR picture
        VARCHAR user_role
        DATE created_date
    }

    MEMBER ||--o{ RESERVATION : "예약"
    MEMBER ||--o{ TODO : "작성"
    GALLERY ||--o{ RESERVATION : "포함"
```

## 🔥 트러블슈팅

프로젝트를 진행하면서 겪은 주요 이슈와 해결 과정을 기록합니다.

---

### 1. 로그아웃 시 500 에러 — Spring Security 6 CSRF Lazy Loading

**문제 상황**

로그인 후 메인 화면에서 로그아웃 버튼을 클릭하면 500 Internal Server Error가 발생했습니다.
로컬 환경에서 간헐적으로 재현되었고, 특히 HTML 파일 내 CSS 코드가 많을 때 발생 빈도가 높았습니다.

**원인 분석**

Spring Security 6은 CSRF 토큰을 **지연(Lazy) 방식**으로 생성합니다.
즉, 요청이 들어왔을 때 CSRF 토큰 객체를 즉시 쿠키에 담지 않고, 토큰 값을 **실제로 참조하는 시점**에 응답 쿠키(`XSRF-TOKEN`)를 발급합니다.

그런데 HTML 내에 인라인 CSS가 과도하게 많아 Thymeleaf 렌더링 시간이 길어지면서,
응답 버퍼가 먼저 flush되어 `Set-Cookie` 헤더가 브라우저에 전달되지 않는 타이밍 문제가 발생했습니다.
결과적으로 로그아웃 POST 요청에 CSRF 토큰이 없거나 불일치하여 Security 필터에서 차단 → 500 응답으로 이어졌습니다.

**해결**

인라인 CSS를 전부 외부 `.css` 파일로 분리하여 렌더링 부하를 줄이고, CSRF 쿠키가 응답에 정상 포함되도록 수정했습니다.
추가로, Security 설정에 `OncePerRequestFilter`를 구현한 `CsrfCookieFilter`를 등록하여 렌더링 전에 CSRF 토큰을 강제로 초기화함으로써 재발을 방지했습니다.

```java
// CsrfCookieFilter — 렌더링 전 CSRF 토큰 강제 로드
public class CsrfCookieFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        CsrfToken csrfToken = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
        csrfToken.getToken(); // 강제 참조 → 쿠키 즉시 발급
        filterChain.doFilter(request, response);
    }
}
```

**배운 점**

Spring Security 6의 CSRF 토큰이 Lazy 방식으로 동작한다는 것을 알게 되었고,
응답 버퍼 flush 타이밍이 보안 동작에 영향을 줄 수 있다는 점, 그리고 HTML 렌더링 최적화가 단순한 성능 문제를 넘어 보안 동작에도 영향을 준다는 사실을 경험했습니다.

---

### 2. Member ↔ Reservation 양방향 매핑 시 Hibernate AnnotationException 발생

**문제 상황**

애플리케이션 기동 시 아래와 같은 예외가 발생하며 서버가 시작되지 않았습니다.

```
org.hibernate.AnnotationException: mappedBy reference an unknown target entity property:
com.study.galleryreservation.domain.reservation.Reservation.member
in com.study.galleryreservation.domain.member.Member.reservations
```

**원인 분석**

`Member` 엔티티에서 `Reservation`과의 관계를 선언할 때 `@OneToMany(mappedBy = "member")`로 작성했으나,
`Reservation` 엔티티의 실제 필드명이 `member`가 아닌 다른 이름(예: `memberEntity`)으로 선언되어 있었습니다.
JPA는 `mappedBy` 값을 통해 연관된 엔티티의 **실제 필드명**을 찾기 때문에, 이름이 불일치하면 매핑 자체가 실패합니다.

```java
// Member.java — mappedBy 값과 실제 필드명 불일치
@OneToMany(mappedBy = "member")  // ← "member"를 찾으나
private List<Reservation> reservations;

// Reservation.java — 실제 필드명이 달랐음
@ManyToOne
@JoinColumn(name = "member_id")
private Member memberEntity;  // ← "memberEntity" 로 선언
```

**해결**

`Reservation` 엔티티의 필드명을 `memberEntity` → `member`로 통일하고,
팀 전체가 ERD를 다시 검토하여 연관관계 방향과 필드 네이밍 규칙을 사전에 합의했습니다.

```java
// 수정 후 — 필드명 일치
@ManyToOne
@JoinColumn(name = "member_id")
private Member member;  // mappedBy = "member" 와 일치
```

**배운 점**

JPA 양방향 관계에서 `mappedBy`는 단순한 문자열이 아닌 **상대 엔티티의 필드명 그 자체**임을 명확히 이해하게 되었습니다.
또한 팀 협업에서 엔티티 설계 시 필드 네이밍 컨벤션을 미리 정의하고 공유하는 것이 중요하다는 것을 배웠습니다.

---

### 3. AWS 배포 후 소셜 로그인 성공해도 로그인 페이지로 리다이렉트

**문제 상황**

로컬 환경에서는 카카오·네이버 소셜 로그인이 정상 동작했으나,
AWS EC2 배포 후 팀원 전원이 소셜 로그인 시도 시 DB 저장까지는 완료되지만 로그인 성공 후 메인 페이지(`/`)가 아닌 로그인 페이지(`/member/login`)로 다시 리다이렉트되는 현상이 발생했습니다.

**원인 분석**

에러 로그를 확인한 결과 아래 메시지가 기록되어 있었습니다.

```
ERROR: null value in column "created_at" violates not-null constraint
```

`Member` 엔티티에 `@PrePersist` 어노테이션이 누락되어 있었습니다.
소셜 로그인 시 신규 사용자를 DB에 저장할 때 `created_at` 컬럼에 `null`이 들어가 PostgreSQL의 NOT NULL 제약 조건을 위반했고,
`CustomOAuth2UserService` 내부에서 예외가 발생해 OAuth2 흐름 자체가 실패했습니다.

로컬에서 문제가 재현되지 않았던 이유는, 로컬 DB에는 이미 소셜 로그인 테스트 계정이 존재하여 `findByEmail()`로 기존 사용자를 조회했기 때문에 새로운 `INSERT`가 발생하지 않았던 것입니다.

```java
// 문제가 된 코드 — @PrePersist 누락
@Entity
public class Member {
    private LocalDateTime createdAt;

    // @PrePersist 가 없어 저장 시점에 createdAt 이 null
    public void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}
```

**해결**

`@PrePersist` 어노테이션을 추가하여 엔티티가 처음 저장되는 시점에 `createdAt`이 자동으로 세팅되도록 수정했습니다.

```java
// 수정 후
@PrePersist
public void onCreate() {
    this.createdAt = LocalDateTime.now();
}
```

**배운 점**

로컬과 배포 환경의 DB 상태 차이로 인해 버그가 숨겨질 수 있다는 것을 체감했습니다.
`@PrePersist`, `@PreUpdate` 같은 JPA 생명주기 콜백 어노테이션의 역할과 누락 시 발생하는 문제를 깊이 이해하게 되었습니다.
또한 배포 환경에서 발생하는 이슈는 반드시 서버 로그를 먼저 확인하는 습관의 중요성을 배웠습니다.

---

## 💙 팀원 소개

| 김태혁(팀장👑)               | 김민준                   | 이유리                  | 박준현             |
| ---------------------------- | ------------------------ | ----------------------- | ------------------ |
| Back-End / DB                | Back-End                 | Back-End                | Back-End / DB      |
| 회원가입 / 로그인            | 할 일(Todo) 기능 구현    | Front-End / UX,UI       | 갤러리 기능 구현   |
| 소셜 로그인 (카카오, 네이버) | 할 일 등록 / 수정 / 삭제 | 예약 기능 구현          | 갤러리 목록 / 상세 |
| Spring Security 설정         |                          | 예약 목록 / 상세 / 취소 |                    |
| 관리자 페이지                |                          |                         |                    |

---
