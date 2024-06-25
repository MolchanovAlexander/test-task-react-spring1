CREATE TABLE categories
(
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    name        VARCHAR(255)          NOT NULL UNIQUE,
    description VARCHAR(255),
    is_deleted  BOOLEAN DEFAULT FALSE NOT NULL
);
