이유리
rieeuly
온라인

#회의 채널의 시작이에요. 
이니언 — 오후 4:59
태혁 공통 기반 (Security, 설정, 공통 레이아웃) + Member 전담

준현 AGallery 전담 (USER 목록/상세 + ADMIN 등록/수정/삭제)

유리 BReservation 전담 (USER 예약/취소 + ADMIN 승인/거절)

민준 CTodo 전담 (작성/수정/삭제/완료 토글) 
김민준 — 오후 5:12
이미지
이니언 — 오후 6:23
CREATE TABLE member (
                        id          BIGINT AUTO_INCREMENT PRIMARY KEY,  -- 회원 고유 식별자
                        username    VARCHAR(50)  NOT NULL UNIQUE,        -- 로그인 아이디
                        password    VARCHAR(255) NOT NULL,               -- 암호화된 비밀번호
                        email       VARCHAR(100) NOT NULL UNIQUE,        -- 이메일 (중복 불가)
                        role        VARCHAR(20)  NOT NULL DEFAULT 'ROLE_USER',  -- 권한 (ROLE_USER / ROLE_ADMIN)

    DB쿼리문.txt
                        4KB
                        컬럼마다 주석 달았습니다.
                        db.sql에 이거 적용해주세요.
    ﻿
                        CREATE TABLE member (
                        id          BIGINT AUTO_INCREMENT PRIMARY KEY,  -- 회원 고유 식별자
                        username    VARCHAR(50)  NOT NULL UNIQUE,        -- 로그인 아이디
    password    VARCHAR(255) NOT NULL,               -- 암호화된 비밀번호
    email       VARCHAR(100) NOT NULL UNIQUE,        -- 이메일 (중복 불가)
    role        VARCHAR(20)  NOT NULL DEFAULT 'ROLE_USER',  -- 권한 (ROLE_USER / ROLE_ADMIN)
    created_at  DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP  -- 가입 일시
    );

CREATE TABLE todo (
                      id          BIGINT AUTO_INCREMENT PRIMARY KEY,   -- 투두 고유 식별자
                      member_id   BIGINT       NOT NULL,               -- 작성한 회원 (member.id 참조)
                      title       VARCHAR(200) NOT NULL,               -- 투두 제목
                      content     TEXT,                                -- 투두 상세 내용
                      is_done     BOOLEAN      NOT NULL DEFAULT FALSE, -- 완료 여부 (false=미완료, true=완료)
                      due_date    DATE,                                -- 마감 기한
                      created_at  DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,  -- 생성 일시
                      updated_at  DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,  -- 수정 일시
                      FOREIGN KEY (member_id) REFERENCES member(id) ON DELETE CASCADE  -- 회원 삭제 시 투두도 삭제
);

CREATE TABLE gallery (
                         id          BIGINT AUTO_INCREMENT PRIMARY KEY,   -- 갤러리 고유 식별자
                         name        VARCHAR(100) NOT NULL,               -- 갤러리 이름
                         location    VARCHAR(255) NOT NULL,               -- 갤러리 위치 (층/구역)
                         description TEXT,                                -- 갤러리 소개
                         capacity    INT          NOT NULL,               -- 최대 수용 인원
                         is_active   BOOLEAN      NOT NULL DEFAULT TRUE,  -- 운영 여부 (true=운영중, false=비활성)
                         created_at  DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,  -- 등록 일시
                         updated_at  DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP  -- 수정 일시
);

CREATE TABLE reservation (
                             id               BIGINT      NOT NULL AUTO_INCREMENT PRIMARY KEY,  -- 예약 고유 식별자
                             member_id        BIGINT      NOT NULL,  -- 예약한 회원 (member.id 참조)
                             gallery_id       BIGINT      NOT NULL,  -- 예약한 갤러리 (gallery.id 참조)
                             reservation_date DATE        NOT NULL,  -- 관람 날짜
                             start_time       TIME        NOT NULL,  -- 관람 시작 시간
                             end_time         TIME        NOT NULL,  -- 관람 종료 시간
                             status           VARCHAR(20) NOT NULL DEFAULT 'PENDING',  -- 예약 상태 (PENDING=대기 / APPROVED=확정 / REJECTED=거절)
                             created_at       DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP,  -- 예약 신청 일시
                             updated_at       DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,  -- 상태 변경 일시
                             FOREIGN KEY (member_id)  REFERENCES member(id)  ON DELETE CASCADE,  -- 회원 삭제 시 예약도 삭제
                             FOREIGN KEY (gallery_id) REFERENCES gallery(id) ON DELETE CASCADE,  -- 갤러리 삭제 시 예약도 삭제
                             INDEX idx_gallery_date (gallery_id, reservation_date)  -- 갤러리별 날짜 조회 성능 향상용 인덱스
);
DB쿼리문.txt
4KB