CREATE TABLE project_member
(
    project_id BIGINT                               NOT NULL,
    user_id    BIGINT                               NOT NULL,
    role       ENUM ('LEADER', 'MANAGER', 'MEMBER') NOT NULL DEFAULT 'MEMBER',
    UNIQUE KEY uk_project_member (project_id, user_id),
    FOREIGN KEY (project_id) REFERENCES project (project_id)
        ON DELETE CASCADE
        ON UPDATE CASCADE,
    FOREIGN KEY (user_id) REFERENCES user (user_id)
        ON DELETE CASCADE
        ON UPDATE CASCADE
)
