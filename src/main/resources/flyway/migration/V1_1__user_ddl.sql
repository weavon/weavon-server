CREATE TABLE user
(
    user_id  BIGINT                 NOT NULL AUTO_INCREMENT,
    username VARCHAR(20)            NOT NULL UNIQUE,
    password VARCHAR(255)           NOT NULL,
    nickname VARCHAR(255)           NOT NULL,
    email    VARCHAR(255) UNIQUE,
    role     ENUM ('ADMIN', 'USER') NOT NULL DEFAULT 'USER',
    PRIMARY KEY (user_id)
);