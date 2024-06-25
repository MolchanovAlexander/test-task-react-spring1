CREATE TABLE users_roles
(
    user_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    PRIMARY KEY (user_id, role_id),
    CONSTRAINT fk_user_id FOREIGN KEY (user_id) REFERENCES users (id),
    CONSTRAINT fk_role_id FOREIGN KEY (role_id) REFERENCES roles (id)
);

INSERT INTO users_roles (user_id, role_id)
VALUES ((SELECT id FROM users WHERE email = 'admin@admin.com'), (SELECT id FROM roles WHERE role = 'ADMIN')),
       ((SELECT id FROM users WHERE email = 'admin@admin.com'), (SELECT id FROM roles WHERE role = 'USER')),
       ((SELECT id FROM users WHERE email = 'alice@alice.com'), (SELECT id FROM roles WHERE role = 'USER'));
