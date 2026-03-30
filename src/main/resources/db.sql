CREATE TABLE member (
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    username    VARCHAR(50)  NOT NULL UNIQUE,
    password    VARCHAR(255) NOT NULL,
    email       VARCHAR(100) NOT NULL UNIQUE,
    role        VARCHAR(20)  NOT NULL DEFAULT 'ROLE_USER',
    created_at  DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE todo (
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    member_id   BIGINT       NOT NULL,
    title       VARCHAR(200) NOT NULL,
    content     TEXT,
    is_done     BOOLEAN      NOT NULL DEFAULT FALSE,
    due_date    DATE,
    created_at  DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at  DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (member_id) REFERENCES member(id) ON DELETE CASCADE
);

CREATE TABLE gallery (
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    name        VARCHAR(100) NOT NULL,
    location    VARCHAR(255) NOT NULL,
    description TEXT,
    capacity    INT          NOT NULL,
    is_active   BOOLEAN      NOT NULL DEFAULT TRUE,
    created_at  DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at  DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE reservation (
    id               BIGINT AUTO_INCREMENT PRIMARY KEY,
    member_id        BIGINT      NOT NULL,
    gallery_id       BIGINT      NOT NULL,
    reservation_date DATE        NOT NULL,
    start_time       TIME        NOT NULL,
    end_time         TIME        NOT NULL,
    status           VARCHAR(20) NOT NULL DEFAULT 'PENDING',
    created_at       DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at       DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (member_id)  REFERENCES member(id)  ON DELETE CASCADE,
    FOREIGN KEY (gallery_id) REFERENCES gallery(id) ON DELETE CASCADE,
    INDEX idx_gallery_date (gallery_id, reservation_date)
);